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
import com.total650.springboot_hub.payload.UpdateProfileDto;
import com.total650.springboot_hub.payload.ChangePasswordDto;
import com.total650.springboot_hub.exception.ResourceNotFoundException;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;

import java.util.HashSet;
import java.util.Set;
import com.total650.springboot_hub.payload.AccountDto;
import org.modelmapper.ModelMapper;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager manager;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private ModelMapper mapper;

    @Autowired
    public AuthServiceImpl(AuthenticationManager manager, AccountRepository accountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, ModelMapper mapper) {
        this.manager = manager;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mapper = mapper;
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

    @Override
    public String updateProfile(UpdateProfileDto updateProfileDto) {
        String userIdentifier = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByUsernameOrEmail(userIdentifier, userIdentifier)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username or email", userIdentifier));

        account.setName(updateProfileDto.getName());
        
        if (updateProfileDto.getProfilePicture() != null) {
            validateAndSetProfilePicture(account, updateProfileDto.getProfilePicture());
        }
        
        accountRepository.save(account);
        return "Profile updated successfully";
    }

    private void validateAndSetProfilePicture(Account account, String base64Image) {
        // Check if string is valid Base64
        if (!isValidBase64(base64Image)) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid image format");
        }

        // Extract image data (remove data:image/jpeg;base64, prefix if present)
        String imageData = base64Image.contains(",") ? 
            base64Image.substring(base64Image.indexOf(",") + 1) : base64Image;

        // Decode Base64 to check size
        byte[] imageBytes = Base64.getDecoder().decode(imageData);
        
        // Check file size (max 1MB)
        if (imageBytes.length > 1024 * 1024) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Image size should be less than 1MB");
        }

        // Validate image type
        String mimeType = getMimeType(imageBytes);
        if (!isValidImageType(mimeType)) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Only JPG, PNG and GIF images are allowed");
        }

        account.setProfilePicture(base64Image);
    }

    private boolean isValidBase64(String base64String) {
        try {
            Base64.getDecoder().decode(
                base64String.contains(",") ? 
                base64String.substring(base64String.indexOf(",") + 1) : 
                base64String
            );
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private String getMimeType(byte[] bytes) {
        try {
            return URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unable to determine image type");
        }
    }

    private boolean isValidImageType(String mimeType) {
        return mimeType != null && (
            mimeType.equals("image/jpeg") ||
            mimeType.equals("image/png") ||
            mimeType.equals("image/gif")
        );
    }

    @Override
    public String changePassword(ChangePasswordDto changePasswordDto) {
        String userIdentifier = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByUsernameOrEmail(userIdentifier, userIdentifier)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username or email", userIdentifier));

        // Verify current password
        if (!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), account.getPassword())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }

        // Update password
        account.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        accountRepository.save(account);
        
        return "Password changed successfully";
    }

    @Override
    public AccountDto getCurrentUser() {
        String userIdentifier = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByUsernameOrEmail(userIdentifier, userIdentifier)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username or email", userIdentifier));
            
        return mapper.map(account, AccountDto.class);
    }
}
