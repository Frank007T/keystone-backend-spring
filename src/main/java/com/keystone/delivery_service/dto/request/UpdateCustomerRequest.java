package com.keystone.delivery_service.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateCustomerRequest {

    private String fullName;

    @Email
    private String email;

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

    private Boolean active;
}