package com.keystone.delivery_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.keystone.delivery_service.dto.request.CustomerRequest;
import com.keystone.delivery_service.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse updateCustomer(Long id, CustomerRequest request);

    CustomerResponse getCustomerById(Long id);

    List<CustomerResponse> getAllCustomers();

    List<CustomerResponse> searchCustomers(String keyword);

    void deleteCustomer(Long id);

    Page<CustomerResponse> getCustomers(
            int page,
            int size,
            String sortBy,
            String direction);
}