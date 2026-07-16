package com.keystone.delivery_service.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String role;

    private boolean enabled;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}