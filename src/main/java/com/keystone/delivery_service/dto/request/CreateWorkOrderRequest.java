package com.keystone.delivery_service.dto.request;

import java.time.LocalDateTime;

import com.keystone.delivery_service.enums.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateWorkOrderRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Priority priority;

    @NotNull
    private Long customerId;

    private Long technicianId;

    private LocalDateTime expectedCompletionDate;

}