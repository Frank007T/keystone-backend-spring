package com.keystone.delivery_service.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {

    private Long id;

    private Long userId;

    private String userName;

    private String title;

    private String message;

    private boolean read;

    private LocalDateTime createdAt;
}