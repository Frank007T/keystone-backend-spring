package com.keystone.delivery_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.keystone.delivery_service.dto.request.LoginRequest;
import com.keystone.delivery_service.dto.request.RegisterRequest;
import com.keystone.delivery_service.dto.response.AuthResponse;
import com.keystone.delivery_service.service.AuthenticationService;

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
}