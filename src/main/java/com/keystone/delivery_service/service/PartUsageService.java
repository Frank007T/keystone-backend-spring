package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.request.CreatePartUsageRequest;
import com.keystone.delivery_service.dto.response.PartUsageResponse;

public interface PartUsageService {

    PartUsageResponse createPartUsage(
            CreatePartUsageRequest request);

    List<PartUsageResponse> getByWorkOrder(
            Long workOrderId);

}