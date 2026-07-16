package com.keystone.delivery_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.delivery_service.entity.PasswordResetOtp;

import org.springframework.transaction.annotation.Transactional;

@Transactional


public interface PasswordResetOtpRepository
        extends JpaRepository<PasswordResetOtp, Long> {

    Optional<PasswordResetOtp> findTopByEmailOrderByCreatedAtDesc(
            String email);

    
    void deleteByEmail(String email);
}