package com.keystone.delivery_service.service;

import com.keystone.delivery_service.dto.request.LoginRequest;
import com.keystone.delivery_service.dto.request.RegisterRequest;
import com.keystone.delivery_service.dto.response.AuthResponse;

public interface AuthenticationService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}