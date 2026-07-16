package com.keystone.delivery_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCustomerRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private String alternatePhone;

    private String companyName;

    private String gstNumber;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private String country;

    private String notes;
}