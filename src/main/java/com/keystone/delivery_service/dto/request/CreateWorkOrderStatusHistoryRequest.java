package com.keystone.delivery_service.dto.request;

import com.keystone.delivery_service.enums.WorkOrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateWorkOrderStatusHistoryRequest {

    @NotNull
    private Long workOrderId;

    @NotNull
    private WorkOrderStatus status;

    private String remarks;
}