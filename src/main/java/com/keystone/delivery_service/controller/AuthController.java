package com.keystone.delivery_service.controller;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.keystone.delivery_service.dto.request.LoginRequest;
import com.keystone.delivery_service.dto.request.RegisterRequest;
import com.keystone.delivery_service.dto.response.AuthResponse;
import com.keystone.delivery_service.service.AuthenticationService;

import com.keystone.delivery_service.dto.request.GoogleLoginRequest;

import com.keystone.delivery_service.dto.request.SendLoginOtpRequest;
import com.keystone.delivery_service.dto.request.LoginWithOtpRequest;
import com.keystone.delivery_service.dto.response.MessageResponse;

import com.keystone.delivery_service.dto.response.UserProfileResponse;

import com.keystone.delivery_service.dto.request.ForgotPasswordRequest;
import com.keystone.delivery_service.dto.response.MessageResponse;

import com.keystone.delivery_service.dto.request.RefreshTokenRequest;
import com.keystone.delivery_service.dto.response.RefreshTokenResponse;
import org.springframework.security.core.Authentication;
import com.keystone.delivery_service.dto.request.VerifyOtpRequest;
import com.keystone.delivery_service.dto.request.ResetPasswordRequest;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authenticationService.login(request));
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser() {

        return ResponseEntity.ok(
                authenticationService.getCurrentUser());
    }
    
    
    
    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {

        return ResponseEntity.ok(
                authenticationService.forgotPassword(request));
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<MessageResponse> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        return ResponseEntity.ok(
                authenticationService.verifyOtp(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        return ResponseEntity.ok(
                authenticationService.resetPassword(request));
    }
    
    @PostMapping("/send-login-otp")
    public ResponseEntity<MessageResponse> sendLoginOtp(
            @Valid @RequestBody SendLoginOtpRequest request) {

        return ResponseEntity.ok(
                authenticationService.sendLoginOtp(request));
    }

    @PostMapping("/login-with-otp")
    public ResponseEntity<AuthResponse> loginWithOtp(
            @Valid @RequestBody LoginWithOtpRequest request) {

        return ResponseEntity.ok(
                authenticationService.loginWithOtp(request));
    }
    
    @PostMapping("/google-login")
    public ResponseEntity<AuthResponse> googleLogin(
            @Valid @RequestBody GoogleLoginRequest request) {

        return ResponseEntity.ok(
                authenticationService.googleLogin(request));
    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(
                authenticationService.refreshToken(request));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(
            Authentication authentication) {

        return ResponseEntity.ok(
                authenticationService.logout(
                        authentication.getName()));
    }
}