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

import com.keystone.delivery_service.entity.RefreshToken;
import com.keystone.delivery_service.service.RefreshTokenService;

import java.time.LocalDateTime;
import java.util.Random;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.keystone.delivery_service.dto.request.GoogleLoginRequest;
import com.keystone.delivery_service.service.GoogleTokenVerifierService;

import com.keystone.delivery_service.dto.request.ForgotPasswordRequest;
import com.keystone.delivery_service.dto.response.MessageResponse;
import com.keystone.delivery_service.entity.PasswordResetOtp;
import com.keystone.delivery_service.repository.PasswordResetOtpRepository;
import com.keystone.delivery_service.service.EmailService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.keystone.delivery_service.dto.response.UserProfileResponse;

import com.keystone.delivery_service.dto.request.VerifyOtpRequest;
import com.keystone.delivery_service.dto.request.ResetPasswordRequest;

import com.keystone.delivery_service.dto.request.LoginWithOtpRequest;
import com.keystone.delivery_service.dto.request.SendLoginOtpRequest;
import com.keystone.delivery_service.entity.LoginOtp;
import com.keystone.delivery_service.repository.LoginOtpRepository;

import com.keystone.delivery_service.dto.request.RefreshTokenRequest;
import com.keystone.delivery_service.dto.response.RefreshTokenResponse;
import com.keystone.delivery_service.entity.RefreshToken;

import org.springframework.transaction.annotation.Transactional;
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    private final PasswordResetOtpRepository passwordResetOtpRepository;
    private final EmailService emailService;
    
    private final LoginOtpRepository loginOtpRepository;
    
    private final GoogleTokenVerifierService googleTokenVerifierService;
    
    private final RefreshTokenService refreshTokenService;
    
    

    
    public AuthenticationServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            PasswordResetOtpRepository passwordResetOtpRepository,
            EmailService emailService,
            GoogleTokenVerifierService googleTokenVerifierService,
            LoginOtpRepository loginOtpRepository,
            RefreshTokenService refreshTokenService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordResetOtpRepository = passwordResetOtpRepository;
        this.emailService = emailService;
        this.loginOtpRepository = loginOtpRepository;
        this.googleTokenVerifierService = googleTokenVerifierService;
        this.refreshTokenService = refreshTokenService;
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

        String accessToken = jwtService.generateToken(user.getEmail());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user.getEmail());

        return new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                "Registration Successful");
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String accessToken =
                jwtService.generateToken(user.getEmail());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user.getEmail());

        return new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                "Login Successful");
    }
    
    
    @Override
    public UserProfileResponse getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return UserProfileResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .enabled(user.isEnabled())
                .build();
    }
    
    @Override
    public MessageResponse forgotPassword(
            ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Email not registered"));

        passwordResetOtpRepository.deleteByEmail(user.getEmail());

        String otp = String.format("%06d",
                new Random().nextInt(999999));

        PasswordResetOtp resetOtp =
                PasswordResetOtp.builder()
                        .email(user.getEmail())
                        .otp(otp)
                        .expiryTime(LocalDateTime.now().plusMinutes(5))
                        .verified(false)
                        .build();

        passwordResetOtpRepository.save(resetOtp);

        emailService.sendOtpEmail(
                user.getEmail(),
                otp);

        return new MessageResponse(
                "OTP sent successfully.");
    }
    
    @Override
    public MessageResponse verifyOtp(
            VerifyOtpRequest request) {

        PasswordResetOtp otp = passwordResetOtpRepository
                .findTopByEmailOrderByCreatedAtDesc(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("OTP not found"));

        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!otp.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        otp.setVerified(true);
        passwordResetOtpRepository.save(otp);

        return new MessageResponse("OTP verified successfully.");
    }
    
    @Override
    public MessageResponse resetPassword(
            ResetPasswordRequest request) {

        PasswordResetOtp otp = passwordResetOtpRepository
                .findTopByEmailOrderByCreatedAtDesc(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("OTP verification required"));

        if (!otp.isVerified()) {
            throw new RuntimeException("OTP not verified");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        passwordResetOtpRepository.deleteByEmail(request.getEmail());

        return new MessageResponse("Password reset successfully.");
    }
    
    @Override
    @Transactional
    public MessageResponse sendLoginOtp(
            SendLoginOtpRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        loginOtpRepository.deleteByEmail(user.getEmail());

        String otp = String.format("%06d",
                new Random().nextInt(1000000));

        LoginOtp loginOtp = LoginOtp.builder()
                .email(user.getEmail())
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .build();

        loginOtpRepository.save(loginOtp);

        emailService.sendOtpEmail(
                user.getEmail(),
                otp);

        return new MessageResponse(
                "Login OTP sent successfully.");
    }
    
    
    @Override
    @Transactional
    public AuthResponse loginWithOtp(
            LoginWithOtpRequest request) {

        LoginOtp loginOtp = loginOtpRepository
                .findTopByEmailOrderByCreatedAtDesc(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("OTP not found"));

        if (loginOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!loginOtp.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        loginOtp.setVerified(true);
        loginOtpRepository.save(loginOtp);
        
        String accessToken =
                jwtService.generateToken(user.getEmail());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user.getEmail());

        loginOtpRepository.deleteByEmail(user.getEmail());

        return new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                "Login Successful");

    }
    
  
    @Override
    public AuthResponse googleLogin(GoogleLoginRequest request) {

        try {

            GoogleIdToken.Payload payload =
                    googleTokenVerifierService.verify(request.getIdToken());

            String email = payload.getEmail();
            String fullName = (String) payload.get("name");

            User user = userRepository.findByEmail(email)
                    .orElse(null);

            if (user == null) {

                user = new User();

                user.setEmail(email);
                user.setFullName(fullName);
                user.setPassword("");
                user.setEnabled(true);
                user.setRole(Role.CUSTOMER);

                userRepository.save(user);
            }

            String accessToken =
                    jwtService.generateToken(user.getEmail());

            RefreshToken refreshToken =
                    refreshTokenService.createRefreshToken(user.getEmail());

            return new AuthResponse(
                    accessToken,
                    refreshToken.getToken(),
                    "Google Login Successful"
            );

        } catch (GeneralSecurityException | IOException e) {

            throw new RuntimeException(
                    "Google authentication failed", e);
        
    
        }
        
    }
    
    @Override
    public RefreshTokenResponse refreshToken(
            RefreshTokenRequest request) {

        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(
                        request.getRefreshToken());

        String accessToken =
                jwtService.generateToken(
                        refreshToken.getEmail());

        return new RefreshTokenResponse(accessToken);
    }
    
    @Override
    public MessageResponse logout(String email) {

        refreshTokenService.deleteByEmail(email);

        return new MessageResponse(
                "Logout Successful");
    }
}