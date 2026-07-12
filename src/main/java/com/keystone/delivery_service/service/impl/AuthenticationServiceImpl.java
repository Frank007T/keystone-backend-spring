package com.keystone.delivery_service.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.keystone.delivery_service.dto.request.LoginRequest;
import com.keystone.delivery_service.dto.request.RegisterRequest;
import com.keystone.delivery_service.dto.response.AuthResponse;
import com.keystone.delivery_service.entity.User;
import com.keystone.delivery_service.enums.Role;
import com.keystone.delivery_service.repository.UserRepository;
import com.keystone.delivery_service.security.JwtService;
import com.keystone.delivery_service.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());

        // Default role is CUSTOMER
        if (request.getRole() == null) {
            user.setRole(Role.CUSTOMER);
        } else {
            user.setRole(request.getRole());
        }

        user.setEnabled(true);

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                "Registration Successful"
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                "Login Successful"
        );
    }
}