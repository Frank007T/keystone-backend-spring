package com.keystone.delivery_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryReportResponse {

    private long totalParts;
    private long activeParts;
    private long inactiveParts;
    private long lowStockParts;
    private long outOfStockParts;

}