package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.request.CreateMaintenanceScheduleRequest;
import com.keystone.delivery_service.dto.response.MaintenanceScheduleResponse;

public interface MaintenanceScheduleService {

    MaintenanceScheduleResponse createSchedule(
            CreateMaintenanceScheduleRequest request);

    List<MaintenanceScheduleResponse> getAllSchedules();

    MaintenanceScheduleResponse getScheduleById(
            Long id);

    void deleteSchedule(
            Long id);

}