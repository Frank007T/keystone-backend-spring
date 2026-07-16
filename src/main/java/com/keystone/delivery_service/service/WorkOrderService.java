package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.request.CreateWorkOrderRequest;
import com.keystone.delivery_service.dto.request.UpdateWorkOrderRequest;
import com.keystone.delivery_service.dto.response.WorkOrderResponse;
import com.keystone.delivery_service.enums.WorkOrderStatus;

public interface WorkOrderService {

    WorkOrderResponse createWorkOrder(CreateWorkOrderRequest request);

    List<WorkOrderResponse> getAllWorkOrders();

    WorkOrderResponse getWorkOrderById(Long id);

    WorkOrderResponse updateWorkOrder(
            Long id,
            UpdateWorkOrderRequest request);

    void deleteWorkOrder(Long id);

    WorkOrderResponse assignTechnician(
            Long workOrderId,
            Long technicianId);

    WorkOrderResponse assignManager(
            Long workOrderId,
            Long managerId);

    WorkOrderResponse updateStatus(
            Long workOrderId,
            WorkOrderStatus status);
}