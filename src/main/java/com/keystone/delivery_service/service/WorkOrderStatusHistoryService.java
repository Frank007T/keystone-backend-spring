package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.request.CreateWorkOrderStatusHistoryRequest;
import com.keystone.delivery_service.dto.response.WorkOrderStatusHistoryResponse;

public interface WorkOrderStatusHistoryService {

    WorkOrderStatusHistoryResponse createHistory(
            CreateWorkOrderStatusHistoryRequest request);

    List<WorkOrderStatusHistoryResponse> getByWorkOrder(
            Long workOrderId);

}