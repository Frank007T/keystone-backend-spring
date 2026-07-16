package com.keystone.delivery_service.service;

public interface EmailService {

    void sendOtpEmail(
            String toEmail,
            String otp);

}