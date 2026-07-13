package com.keystone.delivery_service.dto.response;

import java.time.LocalDateTime;

import com.keystone.delivery_service.enums.Priority;
import com.keystone.delivery_service.enums.WorkOrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkOrderResponse {

    private Long id;

    private String workOrderNumber;

    private String title;

    private String description;

    private Priority priority;

    private WorkOrderStatus status;

    private Long customerId;

    private String customerName;

    private Long managerId;

    private String managerName;

    private Long technicianId;

    private String technicianName;

    private LocalDateTime expectedCompletionDate;

    private LocalDateTime createdAt;

}