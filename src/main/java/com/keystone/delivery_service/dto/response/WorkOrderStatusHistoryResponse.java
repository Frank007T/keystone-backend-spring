package com.keystone.delivery_service.dto.response;

import java.time.LocalDateTime;

import com.keystone.delivery_service.enums.WorkOrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkOrderStatusHistoryResponse {

    private Long id;

    private Long workOrderId;

    private String workOrderNumber;

    private WorkOrderStatus status;

    private String remarks;

    private LocalDateTime createdAt;
}