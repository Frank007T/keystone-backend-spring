package com.keystone.delivery_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.delivery_service.dto.request.CreatePartUsageRequest;
import com.keystone.delivery_service.dto.response.PartUsageResponse;
import com.keystone.delivery_service.entity.Part;
import com.keystone.delivery_service.entity.PartUsage;
import com.keystone.delivery_service.entity.StockMovement;
import com.keystone.delivery_service.entity.WorkOrder;
import com.keystone.delivery_service.enums.StockMovementType;
import com.keystone.delivery_service.repository.PartRepository;
import com.keystone.delivery_service.repository.PartUsageRepository;
import com.keystone.delivery_service.repository.StockMovementRepository;
import com.keystone.delivery_service.repository.WorkOrderRepository;
import com.keystone.delivery_service.service.PartUsageService;

@Service
@Transactional
public class PartUsageServiceImpl implements PartUsageService {

    private final PartUsageRepository partUsageRepository;
    private final PartRepository partRepository;
    private final WorkOrderRepository workOrderRepository;
    private final StockMovementRepository stockMovementRepository;

    public PartUsageServiceImpl(
            PartUsageRepository partUsageRepository,
            PartRepository partRepository,
            WorkOrderRepository workOrderRepository,
            StockMovementRepository stockMovementRepository) {

        this.partUsageRepository = partUsageRepository;
        this.partRepository = partRepository;
        this.workOrderRepository = workOrderRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Override
    public PartUsageResponse createPartUsage(
            CreatePartUsageRequest request) {

        WorkOrder workOrder = workOrderRepository.findById(request.getWorkOrderId())
                .orElseThrow(() ->
                        new RuntimeException("Work Order not found"));

        Part part = partRepository.findById(request.getPartId())
                .orElseThrow(() ->
                        new RuntimeException("Part not found"));

        if (part.getStockQuantity() < request.getQuantityUsed()) {
            throw new RuntimeException("Insufficient stock available");
        }

        // Deduct stock
        part.setStockQuantity(
                part.getStockQuantity() - request.getQuantityUsed());

        partRepository.save(part);

        // Save Part Usage
        PartUsage usage = new PartUsage();
        usage.setWorkOrder(workOrder);
        usage.setPart(part);
        usage.setQuantityUsed(request.getQuantityUsed());
        usage.setUnitCost(part.getUnitCost());

        partUsageRepository.save(usage);

        // Save Stock Movement
        StockMovement movement = new StockMovement();
        movement.setPart(part);
        movement.setMovementType(StockMovementType.STOCK_OUT);
        movement.setQuantity(request.getQuantityUsed());
        movement.setRemarks(
                "Used in Work Order " + workOrder.getWorkOrderNumber());

        stockMovementRepository.save(movement);

        return mapToResponse(usage);
    }

    @Override
    public List<PartUsageResponse> getByWorkOrder(
            Long workOrderId) {

        return partUsageRepository.findByWorkOrderId(workOrderId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PartUsageResponse mapToResponse(
            PartUsage usage) {

        return PartUsageResponse.builder()
                .id(usage.getId())
                .workOrderId(usage.getWorkOrder().getId())
                .workOrderNumber(
                        usage.getWorkOrder().getWorkOrderNumber())
                .partId(usage.getPart().getId())
                .partName(usage.getPart().getPartName())
                .quantityUsed(usage.getQuantityUsed())
                .createdAt(usage.getCreatedAt())
                .build();
    }
}