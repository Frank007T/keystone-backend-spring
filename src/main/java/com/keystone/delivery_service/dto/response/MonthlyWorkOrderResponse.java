package com.keystone.delivery_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonthlyWorkOrderResponse {

    private String month;

    private long totalWorkOrders;
}