package com.keystone.delivery_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardStatsResponse {

    private long totalCustomers;

    private long totalSites;

    private long totalWorkOrders;

    private long newWorkOrders;

    private long inProgressWorkOrders;

    private long completedWorkOrders;

    private long totalManagers;

    private long totalTechnicians;

}