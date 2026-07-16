package com.keystone.delivery_service.dto.request;

import com.keystone.delivery_service.enums.WorkOrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateWorkOrderStatusRequest {

    @NotNull(message = "Status is required")
    private WorkOrderStatus status;

}