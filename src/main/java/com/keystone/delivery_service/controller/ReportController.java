package com.keystone.delivery_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keystone.delivery_service.dto.response.RecentNotificationResponse;
import com.keystone.delivery_service.dto.response.UpcomingMaintenanceResponse;

import com.keystone.delivery_service.dto.response.CustomerReportResponse;
import com.keystone.delivery_service.dto.response.TechnicianReportResponse;
import com.keystone.delivery_service.dto.response.WorkOrderReportResponse;
import com.keystone.delivery_service.service.ReportService;

import com.keystone.delivery_service.dto.response.MonthlyWorkOrderResponse;

import com.keystone.delivery_service.dto.response.StatusSummaryResponse;

import com.keystone.delivery_service.dto.response.LowStockPartResponse;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/work-orders")
    public ResponseEntity<WorkOrderReportResponse> getWorkOrderReport() {

        return ResponseEntity.ok(
                reportService.getWorkOrderReport());
    }

    @GetMapping("/technicians")
    public ResponseEntity<List<TechnicianReportResponse>> getTechnicianReport() {

        return ResponseEntity.ok(
                reportService.getTechnicianReport());
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerReportResponse>> getCustomerReport() {

        return ResponseEntity.ok(
                reportService.getCustomerReport());
    }
    
    @GetMapping("/monthly-work-orders")
    public ResponseEntity<List<MonthlyWorkOrderResponse>> getMonthlyWorkOrderReport() {

        return ResponseEntity.ok(
                reportService.getMonthlyWorkOrderReport());
    }
    
    @GetMapping("/status-summary")
    public ResponseEntity<List<StatusSummaryResponse>> getStatusSummary() {

        return ResponseEntity.ok(
                reportService.getStatusSummary());
    }
    
    @GetMapping("/low-stock")
    public ResponseEntity<List<LowStockPartResponse>> getLowStockParts() {

        return ResponseEntity.ok(
                reportService.getLowStockParts());
    }
    
    @GetMapping("/upcoming-maintenance")
    public ResponseEntity<List<UpcomingMaintenanceResponse>> getUpcomingMaintenance() {

        return ResponseEntity.ok(
                reportService.getUpcomingMaintenance());
    }
    
    @GetMapping("/recent-notifications")
    public ResponseEntity<List<RecentNotificationResponse>> getRecentNotifications() {

        return ResponseEntity.ok(
                reportService.getRecentNotifications());
    }
}

