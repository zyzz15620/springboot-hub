package com.total650.springboot_hub.security;

import com.total650.springboot_hub.entity.Account;
import com.total650.springboot_hub.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private AccountRepository accountRepository;

    /// Autowired annotation is not needed because if a @Service class only has a single parameterized constructor, spring will automatically inject the dependency
    /// Autowired is needed when the constructor has more than 1 params like CommentService
    @Autowired
    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /// This method I just follow the instruction
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found with username or email: " + usernameOrEmail));

        Set<GrantedAuthority> authorities = account
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(account.getEmail(), account.getPassword(), authorities);
    }

    ///WHY DO WE NEED THIS INTERFACE and loadUserByUsername() ???
    //When a request with Basic Authentication headers is sent
    //The request first come to AuthenticationFilter, AuthenticationFilter will return an Authentication Object (containing username and password)
    //The Authentication Object will be passed to AuthenticationManager, this has an authenticate() method.
    //Atm the AuthenticationManager don't know which authentication Provider it has to call because there are 3 different AuthenticationProviders (OAuth2, LDAP, DAO)
    //Each AuthenticationProvider has support() and authenticate() method so that AuthenticationManager can internally call the method to select which provider
    //However all AuthenticationProvider need to uses loadByUsername() from CustomUserDetailsService
    //This is how Database Authentication works

}
