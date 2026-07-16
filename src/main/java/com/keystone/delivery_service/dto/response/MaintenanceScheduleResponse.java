package com.keystone.delivery_service.dto.response;

import java.time.LocalDate;

import com.keystone.delivery_service.enums.MaintenanceFrequency;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaintenanceScheduleResponse {

    private Long id;

    private String scheduleName;

    private String customerName;

    private String siteName;

    private MaintenanceFrequency frequency;

    private LocalDate nextMaintenanceDate;

    private boolean active;
}