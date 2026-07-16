package com.keystone.delivery_service.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTimeLogRequest {

    @NotNull
    private Long workOrderId;

    @NotNull
    private Long technicianId;

    @NotNull
    private BigDecimal hoursWorked;

    @NotBlank
    private String workDescription;

    @NotNull
    private LocalDate workDate;

}