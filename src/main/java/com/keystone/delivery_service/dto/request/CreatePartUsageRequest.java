package com.keystone.delivery_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePartUsageRequest {

    @NotNull
    private Long workOrderId;

    @NotNull
    private Long partId;

    @NotNull
    @Min(1)
    private Integer quantityUsed;
}