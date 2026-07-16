package com.keystone.delivery_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.keystone.delivery_service.dto.request.CreateWorkLogRequest;
import com.keystone.delivery_service.dto.response.WorkLogResponse;
import com.keystone.delivery_service.service.WorkLogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/work-logs")
@Validated
public class WorkLogController {

    private final WorkLogService workLogService;

    public WorkLogController(WorkLogService workLogService) {
        this.workLogService = workLogService;
    }

    // Add Work Log
    @PostMapping
    public ResponseEntity<WorkLogResponse> addWorkLog(
            @Valid @RequestBody CreateWorkLogRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workLogService.addWorkLog(request));
    }

    // Get Work Logs By Work Order
    @GetMapping("/work-order/{workOrderId}")
    public ResponseEntity<List<WorkLogResponse>> getWorkLogsByWorkOrder(
            @PathVariable Long workOrderId) {

        return ResponseEntity.ok(
                workLogService.getWorkLogsByWorkOrder(workOrderId));
    }

    // Delete Work Log
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkLog(
            @PathVariable Long id) {

        workLogService.deleteWorkLog(id);

        return ResponseEntity.ok("Work Log deleted successfully.");
    }

}