package com.keystone.delivery_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.delivery_service.dto.request.CreateStockMovementRequest;
import com.keystone.delivery_service.dto.response.StockMovementResponse;
import com.keystone.delivery_service.entity.Part;
import com.keystone.delivery_service.entity.StockMovement;
import com.keystone.delivery_service.enums.StockMovementType;
import com.keystone.delivery_service.repository.PartRepository;
import com.keystone.delivery_service.repository.StockMovementRepository;
import com.keystone.delivery_service.service.StockMovementService;

@Service
@Transactional
public class StockMovementServiceImpl
        implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final PartRepository partRepository;

    public StockMovementServiceImpl(
            StockMovementRepository stockMovementRepository,
            PartRepository partRepository) {

        this.stockMovementRepository = stockMovementRepository;
        this.partRepository = partRepository;
    }

    @Override
    public StockMovementResponse createStockMovement(
            CreateStockMovementRequest request) {

        Part part = partRepository.findById(request.getPartId())
                .orElseThrow(() ->
                        new RuntimeException("Part not found"));

        if (request.getMovementType() == StockMovementType.STOCK_IN) {

            part.setStockQuantity(
                    part.getStockQuantity() + request.getQuantity());

        } else {

            if (part.getStockQuantity() < request.getQuantity()) {
                throw new RuntimeException("Insufficient stock");
            }

            part.setStockQuantity(
                    part.getStockQuantity() - request.getQuantity());
        }

        partRepository.save(part);

        StockMovement movement = new StockMovement();

        movement.setPart(part);
        movement.setMovementType(request.getMovementType());
        movement.setQuantity(request.getQuantity());
        movement.setRemarks(request.getRemarks());

        stockMovementRepository.save(movement);

        return mapToResponse(movement);
    }

    @Override
    public List<StockMovementResponse> getByPart(
            Long partId) {

        return stockMovementRepository.findByPartId(partId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private StockMovementResponse mapToResponse(
            StockMovement movement) {

        return StockMovementResponse.builder()
                .id(movement.getId())
                .partId(movement.getPart().getId())
                .partName(movement.getPart().getPartName())
                .movementType(movement.getMovementType())
                .quantity(movement.getQuantity())
                .remarks(movement.getRemarks())
                .movementDate(movement.getMovementDate())
                .build();
    }
}