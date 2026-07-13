package com.keystone.delivery_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignTechnicianRequest {

    @NotNull(message = "Technician Id is required")
    private Long technicianId;

}