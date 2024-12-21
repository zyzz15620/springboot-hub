package com.total650.springboot_hub.service.impl;

import com.total650.springboot_hub.payload.LoginDto;
import com.total650.springboot_hub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager manager;

    //Don't need @Autowired, Same reason as CustomUserDetailsService
    public AuthServiceImpl(AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User Logged-in successfully!.";
    }
}
