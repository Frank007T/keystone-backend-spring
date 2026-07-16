package com.keystone.delivery_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignManagerRequest {

    @NotNull(message = "Manager Id is required")
    private Long managerId;

}