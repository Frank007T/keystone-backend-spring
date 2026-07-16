package com.keystone.delivery_service.dto.request;

import java.time.LocalDate;

import com.keystone.delivery_service.enums.MaintenanceFrequency;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMaintenanceScheduleRequest {

    @NotBlank
    private String scheduleName;

    @NotNull
    private Long customerId;

    @NotNull
    private Long siteId;

    @NotNull
    private MaintenanceFrequency frequency;

    @NotNull
    private LocalDate nextMaintenanceDate;
}