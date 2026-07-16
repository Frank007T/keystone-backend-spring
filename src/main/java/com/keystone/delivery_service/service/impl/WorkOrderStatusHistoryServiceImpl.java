package com.keystone.delivery_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.delivery_service.dto.request.CreateWorkOrderStatusHistoryRequest;
import com.keystone.delivery_service.dto.response.WorkOrderStatusHistoryResponse;
import com.keystone.delivery_service.entity.WorkOrder;
import com.keystone.delivery_service.entity.WorkOrderStatusHistory;
import com.keystone.delivery_service.repository.WorkOrderRepository;
import com.keystone.delivery_service.repository.WorkOrderStatusHistoryRepository;
import com.keystone.delivery_service.service.WorkOrderStatusHistoryService;

@Service
@Transactional
public class WorkOrderStatusHistoryServiceImpl
        implements WorkOrderStatusHistoryService {

    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderStatusHistoryRepository historyRepository;

    public WorkOrderStatusHistoryServiceImpl(
            WorkOrderRepository workOrderRepository,
            WorkOrderStatusHistoryRepository historyRepository) {

        this.workOrderRepository = workOrderRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public WorkOrderStatusHistoryResponse createHistory(
            CreateWorkOrderStatusHistoryRequest request) {

        WorkOrder workOrder = workOrderRepository
                .findById(request.getWorkOrderId())
                .orElseThrow(() ->
                        new RuntimeException("Work Order not found"));

        WorkOrderStatusHistory history = new WorkOrderStatusHistory();

        history.setWorkOrder(workOrder);

        // Previous status
        history.setOldStatus(workOrder.getStatus());

        // New status from request
        history.setNewStatus(request.getStatus());

        history.setRemarks(request.getRemarks());

        historyRepository.save(history);

        return mapToResponse(history);
    }

    @Override
    public List<WorkOrderStatusHistoryResponse> getByWorkOrder(
            Long workOrderId) {

        return historyRepository
                .findByWorkOrderIdOrderByCreatedAtDesc(workOrderId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private WorkOrderStatusHistoryResponse mapToResponse(
            WorkOrderStatusHistory history) {

        return WorkOrderStatusHistoryResponse.builder()
                .id(history.getId())
                .workOrderId(history.getWorkOrder().getId())
                .workOrderNumber(history.getWorkOrder().getWorkOrderNumber())

                // New Status
                .status(history.getNewStatus())

                .remarks(history.getRemarks())

                .createdAt(history.getChangedAt())

                .build();
    }
}