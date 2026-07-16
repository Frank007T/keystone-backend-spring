package com.keystone.delivery_service.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.delivery_service.entity.Notification;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    List<Notification> findTop10ByOrderByCreatedAtDesc();

}