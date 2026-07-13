package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.request.CustomerRequest;
import com.keystone.delivery_service.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse updateCustomer(Long id, CustomerRequest request);

    CustomerResponse getCustomerById(Long id);

    List<CustomerResponse> getAllCustomers();

    List<CustomerResponse> searchCustomers(String keyword);

    void deleteCustomer(Long id);

}