package com.keystone.delivery_service.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePartRequest {

    @NotBlank(message = "Part Code is required")
    private String partCode;

    @NotBlank(message = "Part Name is required")
    private String partName;

    private String description;

    @NotNull(message = "Unit Cost is required")
    @DecimalMin(value = "0.0")
    private BigDecimal unitCost;

    @NotNull(message = "Stock Quantity is required")
    @Min(0)
    private Integer stockQuantity;
}