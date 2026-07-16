package com.keystone.delivery_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.keystone.delivery_service.dto.request.CreateWorkOrderStatusHistoryRequest;
import com.keystone.delivery_service.dto.response.WorkOrderStatusHistoryResponse;
import com.keystone.delivery_service.service.WorkOrderStatusHistoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/work-order-history")
@Validated
public class WorkOrderStatusHistoryController {

    private final WorkOrderStatusHistoryService historyService;

    public WorkOrderStatusHistoryController(
            WorkOrderStatusHistoryService historyService) {

        this.historyService = historyService;
    }

    @PostMapping
    public ResponseEntity<WorkOrderStatusHistoryResponse> createHistory(
            @Valid @RequestBody CreateWorkOrderStatusHistoryRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(historyService.createHistory(request));
    }

    @GetMapping("/{workOrderId}")
    public ResponseEntity<List<WorkOrderStatusHistoryResponse>> getHistory(
            @PathVariable Long workOrderId) {

        return ResponseEntity.ok(
                historyService.getByWorkOrder(workOrderId));
    }
}