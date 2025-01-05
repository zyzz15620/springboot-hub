package com.total650.springboot_hub.service;

import com.total650.springboot_hub.payload.JWTAuthResponse;
import com.total650.springboot_hub.payload.LoginDto;
import com.total650.springboot_hub.payload.RegisterDto;

public interface AuthService {
    JWTAuthResponse login(LoginDto loginDto);

    String register(RegisterDto registerDto);

    void delete(Long id);
}
