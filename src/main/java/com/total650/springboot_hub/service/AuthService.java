package com.total650.springboot_hub.service;

import com.total650.springboot_hub.payload.JWTAuthResponse;
import com.total650.springboot_hub.payload.LoginDto;
import com.total650.springboot_hub.payload.RegisterDto;
import com.total650.springboot_hub.payload.UpdateProfileDto;
import com.total650.springboot_hub.payload.ChangePasswordDto;
import com.total650.springboot_hub.payload.AccountDto;

public interface AuthService {
    JWTAuthResponse login(LoginDto loginDto);

    String register(RegisterDto registerDto);

    String updateProfile(UpdateProfileDto updateProfileDto);

    String changePassword(ChangePasswordDto changePasswordDto);

    void delete(Long id);

    AccountDto getCurrentUser();

    AccountDto getUserProfile(Long userId);
}
