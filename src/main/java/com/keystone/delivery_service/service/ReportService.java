package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.response.InventoryReportResponse;
import com.keystone.delivery_service.dto.response.CustomerReportResponse;
import com.keystone.delivery_service.dto.response.TechnicianReportResponse;
import com.keystone.delivery_service.dto.response.WorkOrderReportResponse;

import com.keystone.delivery_service.dto.response.MonthlyWorkOrderResponse;

import com.keystone.delivery_service.dto.response.StatusSummaryResponse;

import com.keystone.delivery_service.dto.response.LowStockPartResponse;

import com.keystone.delivery_service.dto.response.UpcomingMaintenanceResponse;

import com.keystone.delivery_service.dto.response.RecentNotificationResponse;

public interface ReportService {

    WorkOrderReportResponse getWorkOrderReport();

    List<TechnicianReportResponse> getTechnicianReport();
    
    List<CustomerReportResponse> getCustomerReport();
    
    InventoryReportResponse getInventoryReport();
    
    List<MonthlyWorkOrderResponse> getMonthlyWorkOrderReport();
    
    List<StatusSummaryResponse> getStatusSummary();
    
    List<LowStockPartResponse> getLowStockParts();
    
    List<UpcomingMaintenanceResponse> getUpcomingMaintenance();
    
    List<RecentNotificationResponse> getRecentNotifications();

}