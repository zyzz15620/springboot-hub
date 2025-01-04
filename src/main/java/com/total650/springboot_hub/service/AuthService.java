package com.total650.springboot_hub.service;

import com.total650.springboot_hub.payload.LoginDto;
import com.total650.springboot_hub.payload.RegisterDto;


public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

    void delete(Long id);
}
