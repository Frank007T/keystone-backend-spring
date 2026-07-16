package com.keystone.delivery_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TechnicianReportResponse {

    private Long technicianId;

    private String technicianName;

    private long assignedWorkOrders;

    private long completedWorkOrders;

    private double totalHoursWorked;
}