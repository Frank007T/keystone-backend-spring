package com.keystone.delivery_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeLogResponse {

    private Long id;

    private Long workOrderId;
    private String workOrderNumber;

    private Long technicianId;
    private String technicianName;

    private BigDecimal hoursWorked;

    private String workDescription;

    private LocalDate workDate;

    private LocalDateTime createdAt;

}