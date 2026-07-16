package com.keystone.delivery_service.dto.response;

import java.time.LocalDateTime;

import com.keystone.delivery_service.enums.StockMovementType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockMovementResponse {

    private Long id;

    private Long partId;

    private String partName;

    private StockMovementType movementType;

    private Integer quantity;

    private String remarks;

    private LocalDateTime movementDate;

}