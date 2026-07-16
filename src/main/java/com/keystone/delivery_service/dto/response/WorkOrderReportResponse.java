package com.keystone.delivery_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkOrderReportResponse {

    private long totalWorkOrders;

    private long newWorkOrders;

    private long inProgressWorkOrders;

    private long completedWorkOrders;

    private long cancelledWorkOrders;

}