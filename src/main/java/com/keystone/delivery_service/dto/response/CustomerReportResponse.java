package com.keystone.delivery_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerReportResponse {

    private Long customerId;

    private String customerName;

    private long totalSites;

    private long totalWorkOrders;

    private long totalMaintenanceSchedules;

}