package com.total650.springboot_hub.service;

import com.total650.springboot_hub.payload.LoginDto;


public interface AuthService {
    String login(LoginDto loginDto);
}
