package com.keystone.delivery_service.dto.request;

import com.keystone.delivery_service.enums.StockMovementType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateStockMovementRequest {

    @NotNull
    private Long partId;

    @NotNull
    private StockMovementType movementType;

    @NotNull
    @Min(1)
    private Integer quantity;

    private String remarks;

}