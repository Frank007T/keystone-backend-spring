package com.keystone.delivery_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.delivery_service.dto.request.CreateWorkLogRequest;
import com.keystone.delivery_service.dto.response.WorkLogResponse;
import com.keystone.delivery_service.entity.User;
import com.keystone.delivery_service.entity.WorkLog;
import com.keystone.delivery_service.entity.WorkOrder;
import com.keystone.delivery_service.repository.UserRepository;
import com.keystone.delivery_service.repository.WorkLogRepository;
import com.keystone.delivery_service.repository.WorkOrderRepository;
import com.keystone.delivery_service.service.WorkLogService;

@Service
@Transactional
public class WorkLogServiceImpl implements WorkLogService {

    private final WorkLogRepository workLogRepository;
    private final WorkOrderRepository workOrderRepository;
    private final UserRepository userRepository;

    public WorkLogServiceImpl(
            WorkLogRepository workLogRepository,
            WorkOrderRepository workOrderRepository,
            UserRepository userRepository) {

        this.workLogRepository = workLogRepository;
        this.workOrderRepository = workOrderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public WorkLogResponse addWorkLog(CreateWorkLogRequest request) {

        WorkOrder workOrder = workOrderRepository.findById(request.getWorkOrderId())
                .orElseThrow(() -> new RuntimeException("Work Order not found"));

        User technician = userRepository.findById(request.getTechnicianId())
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        WorkLog workLog = new WorkLog();
        workLog.setWorkOrder(workOrder);
        workLog.setTechnician(technician);
        workLog.setRemarks(request.getRemarks());

        workLogRepository.save(workLog);

        return mapToResponse(workLog);
    }

    @Override
    public List<WorkLogResponse> getWorkLogsByWorkOrder(Long workOrderId) {

        return workLogRepository.findByWorkOrderId(workOrderId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteWorkLog(Long id) {

        WorkLog workLog = workLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work Log not found"));

        workLogRepository.delete(workLog);
    }

    private WorkLogResponse mapToResponse(WorkLog workLog) {

        return WorkLogResponse.builder()
                .id(workLog.getId())
                .workOrderId(workLog.getWorkOrder().getId())
                .workOrderNumber(workLog.getWorkOrder().getWorkOrderNumber())
                .technicianId(workLog.getTechnician().getId())
                .technicianName(workLog.getTechnician().getFullName())
                .remarks(workLog.getRemarks())
                .createdAt(workLog.getCreatedAt())
                .build();
    }
}