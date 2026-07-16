package com.keystone.delivery_service.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePartRequest {

    @NotBlank
    private String partCode;

    @NotBlank
    private String partName;

    private String description;

    @NotNull
    @Min(0)
    private BigDecimal unitCost;

    @NotNull
    @Min(0)
    private Integer stockQuantity;

    private boolean active;
}