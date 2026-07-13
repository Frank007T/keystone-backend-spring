package com.keystone.delivery_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.keystone.delivery_service.dto.request.CreateWorkOrderRequest;
import com.keystone.delivery_service.dto.request.UpdateWorkOrderRequest;
import com.keystone.delivery_service.dto.response.WorkOrderResponse;
import com.keystone.delivery_service.service.WorkOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/work-orders")
@Validated
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @PostMapping
    public ResponseEntity<WorkOrderResponse> createWorkOrder(
            @Valid @RequestBody CreateWorkOrderRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workOrderService.createWorkOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<WorkOrderResponse>> getAllWorkOrders() {

        return ResponseEntity.ok(
                workOrderService.getAllWorkOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderResponse> getWorkOrderById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                workOrderService.getWorkOrderById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkOrderResponse> updateWorkOrder(
            @PathVariable Long id,
            @Valid @RequestBody UpdateWorkOrderRequest request) {

        return ResponseEntity.ok(
                workOrderService.updateWorkOrder(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkOrder(
            @PathVariable Long id) {

        workOrderService.deleteWorkOrder(id);

        return ResponseEntity.ok("Work Order deleted successfully.");
    }

}