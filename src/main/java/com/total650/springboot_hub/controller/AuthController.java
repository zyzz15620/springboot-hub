package com.total650.springboot_hub.controller;

import com.total650.springboot_hub.payload.JWTAuthResponse;
import com.total650.springboot_hub.payload.LoginDto;
import com.total650.springboot_hub.payload.RegisterDto;
import com.total650.springboot_hub.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "CRUD REST APIs for Account Resource")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Create JWT REST API", description = "Create JWT REST API is used to generate a JWT for login session")
    @ApiResponse(responseCode = "201", description = "Http Status 201 SUCCESS")
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @Operation(summary = "Create Account REST API", description = "Create Account REST API is used register/signup an account")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
