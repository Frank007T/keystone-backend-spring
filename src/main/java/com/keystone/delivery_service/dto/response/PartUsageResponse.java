package com.keystone.delivery_service.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PartUsageResponse {

    private Long id;

    private Long workOrderId;

    private String workOrderNumber;

    private Long partId;

    private String partName;

    private Integer quantityUsed;

    private LocalDateTime createdAt;
}