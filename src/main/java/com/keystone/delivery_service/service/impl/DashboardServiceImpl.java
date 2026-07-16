package com.keystone.delivery_service.service.impl;

import org.springframework.stereotype.Service;

import com.keystone.delivery_service.dto.response.DashboardStatsResponse;
import com.keystone.delivery_service.enums.Role;
import com.keystone.delivery_service.enums.WorkOrderStatus;
import com.keystone.delivery_service.repository.CustomerRepository;
import com.keystone.delivery_service.repository.SiteRepository;
import com.keystone.delivery_service.repository.UserRepository;
import com.keystone.delivery_service.repository.WorkOrderRepository;
import com.keystone.delivery_service.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final CustomerRepository customerRepository;
    private final SiteRepository siteRepository;
    private final WorkOrderRepository workOrderRepository;
    private final UserRepository userRepository;

    public DashboardServiceImpl(
            CustomerRepository customerRepository,
            SiteRepository siteRepository,
            WorkOrderRepository workOrderRepository,
            UserRepository userRepository) {

        this.customerRepository = customerRepository;
        this.siteRepository = siteRepository;
        this.workOrderRepository = workOrderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DashboardStatsResponse getDashboardStats() {

        return DashboardStatsResponse.builder()

                .totalCustomers(customerRepository.count())

                .totalSites(siteRepository.count())

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

                .totalManagers(
                        userRepository.countByRole(Role.MANAGER))

                .totalTechnicians(
                        userRepository.countByRole(Role.TECHNICIAN))

                .build();
    }

}