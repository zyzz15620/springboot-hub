package com.total650.springboot_hub.controller;

import com.total650.springboot_hub.payload.*;
import com.total650.springboot_hub.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "CRUD REST APIs for Account Resource")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login User REST API", description = "Login User REST API is used to login user to the application")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        JWTAuthResponse jwtAuthResponse = authService.login(loginDto);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @Operation(summary = "Create Account REST API", description = "Create Account REST API is used register/signup an account")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @SecurityRequirement(name = "Bear Authentication")
    public ResponseEntity<String> updateProfile(@Valid @RequestBody UpdateProfileDto updateProfileDto) {
        String response = authService.updateProfile(updateProfileDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @SecurityRequirement(name = "Bear Authentication")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        String response = authService.changePassword(changePasswordDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @SecurityRequirement(name = "Bear Authentication")
    public ResponseEntity<AccountDto> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
