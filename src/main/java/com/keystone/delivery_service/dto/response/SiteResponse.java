package com.keystone.delivery_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SiteResponse {

    private Long id;

    private String siteName;

    private String address;

    private String city;

    private String state;

    private String postalCode;

    private Long customerId;

    private String customerName;
}