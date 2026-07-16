package com.keystone.delivery_service.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.keystone.delivery_service.dto.response.RecentNotificationResponse;
import com.keystone.delivery_service.entity.Notification;
import com.keystone.delivery_service.repository.NotificationRepository;


import java.time.LocalDate;

import com.keystone.delivery_service.dto.response.UpcomingMaintenanceResponse;
import com.keystone.delivery_service.entity.MaintenanceSchedule;


import com.keystone.delivery_service.dto.response.LowStockPartResponse;
import com.keystone.delivery_service.entity.Part;

import com.keystone.delivery_service.dto.response.CustomerReportResponse;
import com.keystone.delivery_service.dto.response.InventoryReportResponse;
import com.keystone.delivery_service.dto.response.TechnicianReportResponse;
import com.keystone.delivery_service.dto.response.WorkOrderReportResponse;
import com.keystone.delivery_service.entity.User;
import com.keystone.delivery_service.enums.Role;
import com.keystone.delivery_service.enums.WorkOrderStatus;
import com.keystone.delivery_service.repository.CustomerRepository;
import com.keystone.delivery_service.repository.MaintenanceScheduleRepository;
import com.keystone.delivery_service.repository.PartRepository;
import com.keystone.delivery_service.repository.SiteRepository;
import com.keystone.delivery_service.repository.TimeLogRepository;
import com.keystone.delivery_service.repository.UserRepository;
import com.keystone.delivery_service.repository.WorkOrderRepository;
import com.keystone.delivery_service.service.ReportService;


import java.time.Month;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import com.keystone.delivery_service.dto.response.MonthlyWorkOrderResponse;
import com.keystone.delivery_service.entity.WorkOrder; 

import java.util.Arrays;
import com.keystone.delivery_service.dto.response.StatusSummaryResponse;

@Service
public class ReportServiceImpl implements ReportService {

    private final WorkOrderRepository workOrderRepository;
    private final UserRepository userRepository;
    private final TimeLogRepository timeLogRepository;
    private final CustomerRepository customerRepository;
    private final SiteRepository siteRepository;
    private final MaintenanceScheduleRepository maintenanceScheduleRepository;
    private final PartRepository partRepository;
    private final NotificationRepository notificationRepository;

    public ReportServiceImpl(
            WorkOrderRepository workOrderRepository,
            UserRepository userRepository,
            TimeLogRepository timeLogRepository,
            CustomerRepository customerRepository,
            SiteRepository siteRepository,
            MaintenanceScheduleRepository maintenanceScheduleRepository,
            PartRepository partRepository,
            NotificationRepository notificationRepository) {

        this.workOrderRepository = workOrderRepository;
        this.userRepository = userRepository;
        this.timeLogRepository = timeLogRepository;
        this.customerRepository = customerRepository;
        this.siteRepository = siteRepository;
        this.maintenanceScheduleRepository = maintenanceScheduleRepository;
        this.partRepository = partRepository;
        this.notificationRepository = notificationRepository;
    }
    @Override
    public WorkOrderReportResponse getWorkOrderReport() {

        return WorkOrderReportResponse.builder()

                .totalWorkOrders(workOrderRepository.count())

                .newWorkOrders(
                        workOrderRepository.countByStatus(
                                WorkOrderStatus.NEW))

                .inProgressWorkOrders(
                        workOrderRepository.countByStatus(
                                WorkOrderStatus.IN_PROGRESS))

                .completedWorkOrders(
                        workOrderRepository.countByStatus(
                                WorkOrderStatus.COMPLETED))

                .cancelledWorkOrders(
                        workOrderRepository.countByStatus(
                                WorkOrderStatus.CANCELLED))

                .build();
    }

    @Override
    public List<TechnicianReportResponse> getTechnicianReport() {

        List<User> technicians =
                userRepository.findByRole(Role.TECHNICIAN);

        return technicians.stream()
                .map(technician -> {

                    BigDecimal hours =
                            timeLogRepository.getTotalHoursWorked(
                                    technician.getId());

                    if (hours == null) {
                        hours = BigDecimal.ZERO;
                    }

                    return TechnicianReportResponse.builder()

                            .technicianId(
                                    technician.getId())

                            .technicianName(
                                    technician.getFullName())

                            .assignedWorkOrders(
                                    workOrderRepository.countByTechnicianId(
                                            technician.getId()))

                            .completedWorkOrders(
                                    workOrderRepository.countByTechnicianIdAndStatus(
                                            technician.getId(),
                                            WorkOrderStatus.COMPLETED))

                            .totalHoursWorked(
                                    hours.doubleValue())

                            .build();

                })
                .toList();
    }

    @Override
    public List<CustomerReportResponse> getCustomerReport() {

        return customerRepository.findAll()
                .stream()
                .map(customer ->

                    CustomerReportResponse.builder()

                            .customerId(
                                    customer.getId())

                            .customerName(
                                    customer.getCustomerName())

                            .totalSites(
                                    siteRepository.countByCustomerId(
                                            customer.getId()))

                            .totalWorkOrders(
                                    workOrderRepository.countByCustomerId(
                                            customer.getId()))

                            .totalMaintenanceSchedules(
                                    maintenanceScheduleRepository.countByCustomerId(
                                            customer.getId()))

                            .build()

                )
                .toList();
    }

    @Override
    public InventoryReportResponse getInventoryReport() {

        return InventoryReportResponse.builder()

                .totalParts(
                        partRepository.count())

                .activeParts(
                        partRepository.countByActiveTrue())

                .inactiveParts(
                        partRepository.countByActiveFalse())

                .lowStockParts(
                        partRepository.countByStockQuantityLessThanEqual(5))

                .outOfStockParts(
                        partRepository.countByStockQuantity(0))

                .build();
    }
    
    @Override
    public List<MonthlyWorkOrderResponse> getMonthlyWorkOrderReport() {

        List<WorkOrder> workOrders = workOrderRepository.findAll();

        Map<Month, Long> monthlyData = workOrders.stream()
                .collect(Collectors.groupingBy(
                        workOrder -> workOrder.getCreatedAt().getMonth(),
                        Collectors.counting()));

        List<MonthlyWorkOrderResponse> response = new ArrayList<>();

        for (Month month : Month.values()) {

            response.add(
                    MonthlyWorkOrderResponse.builder()
                            .month(month.name())
                            .totalWorkOrders(
                                    monthlyData.getOrDefault(month, 0L))
                            .build());
        }

        return response;
    }
    
    @Override
    public List<StatusSummaryResponse> getStatusSummary() {

        return Arrays.stream(WorkOrderStatus.values())
                .map(status ->

                    StatusSummaryResponse.builder()

                            .status(status.name())

                            .total(
                                    workOrderRepository.countByStatus(status))

                            .build()

                )
                .toList();
    }
    
    @Override
    public List<LowStockPartResponse> getLowStockParts() {

        return partRepository
                .findByStockQuantityLessThanEqual(5)
                .stream()
                .map(part ->

                    LowStockPartResponse.builder()

                            .partId(part.getId())

                            .partCode(part.getPartCode())

                            .partName(part.getPartName())

                            .stockQuantity(part.getStockQuantity())

                            .active(part.isActive())

                            .build()

                )
                .toList();
    }
    
    @Override
    public List<UpcomingMaintenanceResponse> getUpcomingMaintenance() {

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        return maintenanceScheduleRepository
                .findByNextMaintenanceDateBetween(today, nextWeek)
                .stream()
                .map(schedule ->

                    UpcomingMaintenanceResponse.builder()

                            .scheduleId(
                                    schedule.getId())

                            .scheduleName(
                                    schedule.getScheduleName())

                            .customerName(
                                    schedule.getCustomer().getCustomerName())

                            .siteName(
                                    schedule.getSite().getSiteName())

                            .nextMaintenanceDate(
                                    schedule.getNextMaintenanceDate())

                            .frequency(
                                    schedule.getFrequency().name())

                            .build()

                )
                .toList();
    }
    
    @Override
    public List<RecentNotificationResponse> getRecentNotifications() {

        return notificationRepository
                .findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(notification ->

                    RecentNotificationResponse.builder()

                            .notificationId(
                                    notification.getId())

                            .title(
                                    notification.getTitle())

                            .message(
                                    notification.getMessage())

                            .userName(
                                    notification.getUser().getFullName())

                            .read(
                                    notification.isRead())

                            .createdAt(
                                    notification.getCreatedAt())

                            .build()

                )
                .toList();
    }
}