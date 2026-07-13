package com.keystone.delivery_service.dto.request;

import java.time.LocalDateTime;

import com.keystone.delivery_service.enums.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateWorkOrderRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Customer Id is required")
    private Long customerId;

    private Long technicianId;

    private Long managerId;

    @NotNull(message = "Expected Completion Date is required")
    private LocalDateTime expectedCompletionDate;

}