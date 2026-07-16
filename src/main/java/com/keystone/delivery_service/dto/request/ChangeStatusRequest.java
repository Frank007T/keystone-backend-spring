package com.keystone.delivery_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeStatusRequest {

    @NotNull(message = "Status is required")
    private Boolean enabled;

}