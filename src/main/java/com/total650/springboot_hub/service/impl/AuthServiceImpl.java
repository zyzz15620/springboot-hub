package com.total650.springboot_hub.service.impl;

import com.total650.springboot_hub.entity.Account;
import com.total650.springboot_hub.entity.Role;
import com.total650.springboot_hub.exception.BlogAPIException;
import com.total650.springboot_hub.payload.LoginDto;
import com.total650.springboot_hub.payload.RegisterDto;
import com.total650.springboot_hub.payload.JWTAuthResponse;
import com.total650.springboot_hub.repository.AccountRepository;
import com.total650.springboot_hub.repository.RoleRepository;
import com.total650.springboot_hub.security.JwtTokenProvider;
import com.total650.springboot_hub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager manager;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(AuthenticationManager manager, AccountRepository accountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.manager = manager;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JWTAuthResponse login(LoginDto loginDto) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        
        // Get user details for additional response info
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        return new JWTAuthResponse(
                token,
                "Bearer",
                role,
                userDetails.getUsername()
        );
    }

    @Override
    public String register(RegisterDto registerDto) {

        //Check if username or email exists
        if(accountRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exist!. ");
        } if(accountRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exist!. ");
        }

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);

        Account account = new Account();
        account.setEmail(registerDto.getEmail());
        account.setName(registerDto.getName());
        account.setUsername(registerDto.getUsername());
        account.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        account.setRoles(roles);

        accountRepository.save(account);
        return "User registered successfully!. ";
    }

    @Override
    public void delete(Long id) {
        accountRepository.existsById(id);
    }
}
