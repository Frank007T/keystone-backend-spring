package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.request.CreateStockMovementRequest;
import com.keystone.delivery_service.dto.response.StockMovementResponse;

public interface StockMovementService {

    StockMovementResponse createStockMovement(
            CreateStockMovementRequest request);

    List<StockMovementResponse> getByPart(
            Long partId);

}