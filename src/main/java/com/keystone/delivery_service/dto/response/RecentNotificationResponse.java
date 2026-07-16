package com.keystone.delivery_service.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecentNotificationResponse {

    private Long notificationId;

    private String title;

    private String message;

    private String userName;

    private boolean read;

    private LocalDateTime createdAt;

}