package com.keystone.delivery_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.keystone.delivery_service.dto.request.CreateTimeLogRequest;
import com.keystone.delivery_service.dto.response.TimeLogResponse;
import com.keystone.delivery_service.service.TimeLogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/time-logs")
@Validated
public class TimeLogController {

    private final TimeLogService timeLogService;

    public TimeLogController(TimeLogService timeLogService) {
        this.timeLogService = timeLogService;
    }

    @PostMapping
    public ResponseEntity<TimeLogResponse> createTimeLog(
            @Valid @RequestBody CreateTimeLogRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(timeLogService.createTimeLog(request));
    }

    @GetMapping
    public ResponseEntity<List<TimeLogResponse>> getAllTimeLogs() {

        return ResponseEntity.ok(
                timeLogService.getAllTimeLogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeLogResponse> getTimeLogById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                timeLogService.getTimeLogById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimeLog(
            @PathVariable Long id) {

        timeLogService.deleteTimeLog(id);

        return ResponseEntity.ok("Time Log deleted successfully.");
    }
}