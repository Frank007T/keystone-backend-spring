package com.keystone.delivery_service.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.keystone.delivery_service.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(
            JavaMailSender mailSender) {

        this.mailSender = mailSender;
    }

    @Override
    public void sendOtpEmail(
            String toEmail,
            String otp) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(toEmail);

        message.setSubject(
                "Keystone Password Reset OTP");

        message.setText(
                """
                Hello,

                Your OTP for password reset is:

                %s

                This OTP is valid for 5 minutes.

                If you didn't request this, please ignore this email.

                Regards,
                Keystone Delivery Service
                """.formatted(otp));

        mailSender.send(message);
    }
}