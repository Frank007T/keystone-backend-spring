package com.keystone.delivery_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LowStockPartResponse {

    private Long partId;

    private String partCode;

    private String partName;

    private Integer stockQuantity;

    private boolean active;

}