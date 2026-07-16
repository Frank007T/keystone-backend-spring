package com.keystone.delivery_service.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpcomingMaintenanceResponse {

    private Long scheduleId;

    private String scheduleName;

    private String customerName;

    private String siteName;

    private LocalDate nextMaintenanceDate;

    private String frequency;

}