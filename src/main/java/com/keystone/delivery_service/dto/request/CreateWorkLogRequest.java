package com.keystone.delivery_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateWorkLogRequest {

    @NotNull(message = "Work Order Id is required")
    private Long workOrderId;

    @NotNull(message = "Technician Id is required")
    private Long technicianId;

    @NotBlank(message = "Remarks is required")
    private String remarks;

}