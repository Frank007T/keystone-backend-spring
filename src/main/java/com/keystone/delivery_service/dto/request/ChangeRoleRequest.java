package com.keystone.delivery_service.dto.request;

import com.keystone.delivery_service.enums.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeRoleRequest {

    @NotNull(message = "Role is required")
    private Role role;

}