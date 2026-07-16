package com.keystone.delivery_service.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.delivery_service.entity.RefreshToken;
import com.keystone.delivery_service.repository.RefreshTokenRepository;
import com.keystone.delivery_service.service.RefreshTokenService;

@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    public RefreshTokenServiceImpl(
            RefreshTokenRepository refreshTokenRepository) {

        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken createRefreshToken(String email) {

        refreshTokenRepository.deleteByEmail(email);

        RefreshToken refreshToken = RefreshToken.builder()
                .email(email)
                .token(UUID.randomUUID().toString())
                .expiryTime(
                        LocalDateTime.now()
                                .plusSeconds(refreshExpiration / 1000))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid Refresh Token"));

        if (refreshToken.getExpiryTime().isBefore(LocalDateTime.now())) {

            refreshTokenRepository.delete(refreshToken);

            throw new RuntimeException("Refresh Token Expired");
        }

        return refreshToken;
    }

    @Override
    public void deleteByEmail(String email) {

        refreshTokenRepository.deleteByEmail(email);
    }
}