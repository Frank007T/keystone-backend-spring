package com.keystone.delivery_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.delivery_service.dto.request.CreateTimeLogRequest;
import com.keystone.delivery_service.dto.response.TimeLogResponse;
import com.keystone.delivery_service.entity.TimeLog;
import com.keystone.delivery_service.entity.User;
import com.keystone.delivery_service.entity.WorkOrder;
import com.keystone.delivery_service.repository.TimeLogRepository;
import com.keystone.delivery_service.repository.UserRepository;
import com.keystone.delivery_service.repository.WorkOrderRepository;
import com.keystone.delivery_service.service.TimeLogService;

@Service
@Transactional
public class TimeLogServiceImpl implements TimeLogService {

    private final TimeLogRepository timeLogRepository;
    private final WorkOrderRepository workOrderRepository;
    private final UserRepository userRepository;

    public TimeLogServiceImpl(
            TimeLogRepository timeLogRepository,
            WorkOrderRepository workOrderRepository,
            UserRepository userRepository) {

        this.timeLogRepository = timeLogRepository;
        this.workOrderRepository = workOrderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TimeLogResponse createTimeLog(CreateTimeLogRequest request) {

        WorkOrder workOrder = workOrderRepository.findById(request.getWorkOrderId())
                .orElseThrow(() -> new RuntimeException("Work Order not found"));

        User technician = userRepository.findById(request.getTechnicianId())
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        TimeLog timeLog = new TimeLog();

        timeLog.setWorkOrder(workOrder);
        timeLog.setTechnician(technician);
        timeLog.setHoursWorked(request.getHoursWorked());
        timeLog.setWorkDescription(request.getWorkDescription());
        timeLog.setWorkDate(request.getWorkDate());

        timeLogRepository.save(timeLog);

        return mapToResponse(timeLog);
    }

    @Override
    public List<TimeLogResponse> getAllTimeLogs() {

        return timeLogRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TimeLogResponse getTimeLogById(Long id) {

        TimeLog timeLog = timeLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Time Log not found"));

        return mapToResponse(timeLog);
    }

    @Override
    public void deleteTimeLog(Long id) {

        TimeLog timeLog = timeLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Time Log not found"));

        timeLogRepository.delete(timeLog);
    }

    private TimeLogResponse mapToResponse(TimeLog timeLog) {

        return TimeLogResponse.builder()
                .id(timeLog.getId())

                .workOrderId(timeLog.getWorkOrder().getId())
                .workOrderNumber(timeLog.getWorkOrder().getWorkOrderNumber())

                .technicianId(timeLog.getTechnician().getId())
                .technicianName(timeLog.getTechnician().getFullName())

                .hoursWorked(timeLog.getHoursWorked())

                .workDescription(timeLog.getWorkDescription())

                .workDate(timeLog.getWorkDate())

                .createdAt(timeLog.getCreatedAt())

                .build();
    }
}