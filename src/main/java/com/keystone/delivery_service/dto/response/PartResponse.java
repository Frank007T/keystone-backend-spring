package com.keystone.delivery_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PartResponse {

    private Long id;

    private String partCode;

    private String partName;

    private String description;

    private BigDecimal unitCost;

    private Integer stockQuantity;

    private Boolean active;

    private LocalDateTime createdAt;
}