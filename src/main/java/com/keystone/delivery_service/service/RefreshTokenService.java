package com.keystone.delivery_service.service;

import com.keystone.delivery_service.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String email);

    RefreshToken verifyRefreshToken(String token);

    void deleteByEmail(String email);
}