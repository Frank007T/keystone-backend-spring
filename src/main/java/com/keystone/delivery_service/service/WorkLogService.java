package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.request.CreateWorkLogRequest;
import com.keystone.delivery_service.dto.response.WorkLogResponse;

public interface WorkLogService {

    WorkLogResponse addWorkLog(CreateWorkLogRequest request);

    List<WorkLogResponse> getWorkLogsByWorkOrder(Long workOrderId);

    void deleteWorkLog(Long id);

}