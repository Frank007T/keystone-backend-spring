package com.keystone.delivery_service.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkLogResponse {

    private Long id;

    private Long workOrderId;

    private String workOrderNumber;

    private Long technicianId;

    private String technicianName;

    private String remarks;

    private LocalDateTime createdAt;

}