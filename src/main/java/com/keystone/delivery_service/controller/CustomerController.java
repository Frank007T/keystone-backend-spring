package com.keystone.delivery_service.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.keystone.delivery_service.dto.request.CustomerRequest;
import com.keystone.delivery_service.dto.response.CustomerResponse;
import com.keystone.delivery_service.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /*
    |--------------------------------------------------------------------------
    | CREATE CUSTOMER
    |--------------------------------------------------------------------------
    */

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.createCustomer(request));
    }

    /*
    |--------------------------------------------------------------------------
    | GET CUSTOMERS (Pagination + Sorting)
    |--------------------------------------------------------------------------
    */

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getCustomers(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "customerName")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction) {

        return ResponseEntity.ok(
                customerService.getCustomers(
                        page,
                        size,
                        sortBy,
                        direction));
    }

    /*
    |--------------------------------------------------------------------------
    | GET CUSTOMER BY ID
    |--------------------------------------------------------------------------
    */

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                customerService.getCustomerById(id));
    }

    /*
    |--------------------------------------------------------------------------
    | SEARCH CUSTOMERS
    |--------------------------------------------------------------------------
    */

    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponse>> searchCustomers(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                customerService.searchCustomers(keyword));
    }

    /*
    |--------------------------------------------------------------------------
    | UPDATE CUSTOMER
    |--------------------------------------------------------------------------
    */

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {

        return ResponseEntity.ok(
                customerService.updateCustomer(id, request));
    }

    /*
    |--------------------------------------------------------------------------
    | DELETE CUSTOMER
    |--------------------------------------------------------------------------
    */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.ok(
                "Customer deleted successfully");
    }

}