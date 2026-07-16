package com.keystone.delivery_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.delivery_service.dto.request.CreateMaintenanceScheduleRequest;
import com.keystone.delivery_service.dto.response.MaintenanceScheduleResponse;
import com.keystone.delivery_service.entity.Customer;
import com.keystone.delivery_service.entity.MaintenanceSchedule;
import com.keystone.delivery_service.entity.Site;
import com.keystone.delivery_service.repository.CustomerRepository;
import com.keystone.delivery_service.repository.MaintenanceScheduleRepository;
import com.keystone.delivery_service.repository.SiteRepository;
import com.keystone.delivery_service.service.MaintenanceScheduleService;

@Service
@Transactional
public class MaintenanceScheduleServiceImpl
        implements MaintenanceScheduleService {

    private final MaintenanceScheduleRepository maintenanceScheduleRepository;
    private final CustomerRepository customerRepository;
    private final SiteRepository siteRepository;

    public MaintenanceScheduleServiceImpl(
            MaintenanceScheduleRepository maintenanceScheduleRepository,
            CustomerRepository customerRepository,
            SiteRepository siteRepository) {

        this.maintenanceScheduleRepository = maintenanceScheduleRepository;
        this.customerRepository = customerRepository;
        this.siteRepository = siteRepository;
    }

    @Override
    public MaintenanceScheduleResponse createSchedule(
            CreateMaintenanceScheduleRequest request) {

        Customer customer = customerRepository.findById(
                request.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Site site = siteRepository.findById(
                request.getSiteId())
                .orElseThrow(() ->
                        new RuntimeException("Site not found"));

        MaintenanceSchedule schedule = new MaintenanceSchedule();

        schedule.setScheduleName(request.getScheduleName());
        schedule.setCustomer(customer);
        schedule.setSite(site);
        schedule.setFrequency(request.getFrequency());
        schedule.setNextMaintenanceDate(
                request.getNextMaintenanceDate());
        schedule.setActive(true);

        maintenanceScheduleRepository.save(schedule);

        return mapToResponse(schedule);
    }

    @Override
    public List<MaintenanceScheduleResponse> getAllSchedules() {

        return maintenanceScheduleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public MaintenanceScheduleResponse getScheduleById(
            Long id) {

        MaintenanceSchedule schedule =
                maintenanceScheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Maintenance Schedule not found"));

        return mapToResponse(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {

        MaintenanceSchedule schedule =
                maintenanceScheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Maintenance Schedule not found"));

        maintenanceScheduleRepository.delete(schedule);
    }

    private MaintenanceScheduleResponse mapToResponse(
            MaintenanceSchedule schedule) {

        return MaintenanceScheduleResponse.builder()
                .id(schedule.getId())
                .scheduleName(schedule.getScheduleName())
                .customerName(
                        schedule.getCustomer().getCustomerName())
                .siteName(
                        schedule.getSite().getSiteName())
                .frequency(schedule.getFrequency())
                .nextMaintenanceDate(
                        schedule.getNextMaintenanceDate())
                .active(schedule.isActive())
                .build();
    }
}