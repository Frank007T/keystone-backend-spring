package com.keystone.delivery_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.keystone.delivery_service.dto.request.CreateMaintenanceScheduleRequest;
import com.keystone.delivery_service.dto.response.MaintenanceScheduleResponse;
import com.keystone.delivery_service.service.MaintenanceScheduleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/maintenance-schedules")
@Validated
public class MaintenanceScheduleController {

    private final MaintenanceScheduleService maintenanceScheduleService;

    public MaintenanceScheduleController(
            MaintenanceScheduleService maintenanceScheduleService) {

        this.maintenanceScheduleService = maintenanceScheduleService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceScheduleResponse> createSchedule(
            @Valid @RequestBody CreateMaintenanceScheduleRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(maintenanceScheduleService.createSchedule(request));
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceScheduleResponse>> getAllSchedules() {

        return ResponseEntity.ok(
                maintenanceScheduleService.getAllSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceScheduleResponse> getScheduleById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                maintenanceScheduleService.getScheduleById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(
            @PathVariable Long id) {

        maintenanceScheduleService.deleteSchedule(id);

        return ResponseEntity.ok(
                "Maintenance Schedule deleted successfully.");
    }
}