package com.keystone.delivery_service.service;

import com.keystone.delivery_service.dto.request.ForgotPasswordRequest;
import com.keystone.delivery_service.dto.request.LoginRequest;
import com.keystone.delivery_service.dto.request.LoginWithOtpRequest;
import com.keystone.delivery_service.dto.request.RegisterRequest;
import com.keystone.delivery_service.dto.request.ResetPasswordRequest;
import com.keystone.delivery_service.dto.request.SendLoginOtpRequest;
import com.keystone.delivery_service.dto.request.VerifyOtpRequest;
import com.keystone.delivery_service.dto.response.AuthResponse;
import com.keystone.delivery_service.dto.response.MessageResponse;
import com.keystone.delivery_service.dto.response.UserProfileResponse;
import com.keystone.delivery_service.dto.request.GoogleLoginRequest;
import com.keystone.delivery_service.dto.request.RefreshTokenRequest;
import com.keystone.delivery_service.dto.response.RefreshTokenResponse;

public interface AuthenticationService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserProfileResponse getCurrentUser();

    MessageResponse forgotPassword(ForgotPasswordRequest request);

    MessageResponse verifyOtp(VerifyOtpRequest request);

    MessageResponse resetPassword(ResetPasswordRequest request);

    // ===============================
    // LOGIN USING EMAIL OTP
    // ===============================

    MessageResponse sendLoginOtp(
            SendLoginOtpRequest request);

    AuthResponse loginWithOtp(
            LoginWithOtpRequest request);
    
    AuthResponse googleLogin(
            GoogleLoginRequest request);
    
    RefreshTokenResponse refreshToken(
            RefreshTokenRequest request);

    MessageResponse logout(String email);

}