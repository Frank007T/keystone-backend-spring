package com.keystone.delivery_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.delivery_service.entity.LoginOtp;

@Repository
public interface LoginOtpRepository
        extends JpaRepository<LoginOtp, Long> {

    Optional<LoginOtp> findTopByEmailOrderByCreatedAtDesc(
            String email);

    @Transactional
    @Modifying
    void deleteByEmail(String email);

}