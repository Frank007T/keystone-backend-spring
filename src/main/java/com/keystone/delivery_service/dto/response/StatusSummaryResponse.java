package com.keystone.delivery_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusSummaryResponse {

    private String status;

    private long total;

}