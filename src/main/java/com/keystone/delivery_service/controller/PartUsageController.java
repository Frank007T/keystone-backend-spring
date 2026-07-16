package com.keystone.delivery_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.keystone.delivery_service.dto.request.CreatePartUsageRequest;
import com.keystone.delivery_service.dto.response.PartUsageResponse;
import com.keystone.delivery_service.service.PartUsageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/part-usage")
@Validated
public class PartUsageController {

    private final PartUsageService partUsageService;

    public PartUsageController(
            PartUsageService partUsageService) {

        this.partUsageService = partUsageService;
    }

    @PostMapping
    public ResponseEntity<PartUsageResponse> createPartUsage(
            @Valid @RequestBody CreatePartUsageRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(partUsageService.createPartUsage(request));
    }

    @GetMapping("/work-order/{workOrderId}")
    public ResponseEntity<List<PartUsageResponse>> getByWorkOrder(
            @PathVariable Long workOrderId) {

        return ResponseEntity.ok(
                partUsageService.getByWorkOrder(workOrderId));
    }

}