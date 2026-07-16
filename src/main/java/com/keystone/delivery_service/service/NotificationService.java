package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.request.CreateNotificationRequest;
import com.keystone.delivery_service.dto.response.NotificationResponse;

public interface NotificationService {

    NotificationResponse createNotification(
            CreateNotificationRequest request);

    List<NotificationResponse> getNotificationsByUser(
            Long userId);

    NotificationResponse markAsRead(
            Long notificationId);

}