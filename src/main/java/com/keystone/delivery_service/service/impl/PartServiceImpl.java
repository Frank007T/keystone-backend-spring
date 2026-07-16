package com.keystone.delivery_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.delivery_service.dto.request.CreatePartRequest;
import com.keystone.delivery_service.dto.request.UpdatePartRequest;
import com.keystone.delivery_service.dto.response.PartResponse;
import com.keystone.delivery_service.entity.Part;
import com.keystone.delivery_service.repository.PartRepository;
import com.keystone.delivery_service.service.PartService;

@Service
@Transactional
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    public PartServiceImpl(
            PartRepository partRepository) {

        this.partRepository = partRepository;
    }

    @Override
    public PartResponse createPart(
            CreatePartRequest request) {

        if (partRepository.existsByPartCode(request.getPartCode())) {
            throw new RuntimeException("Part code already exists");
        }

        Part part = new Part();

        part.setPartCode(request.getPartCode());
        part.setPartName(request.getPartName());
        part.setDescription(request.getDescription());
        part.setUnitCost(request.getUnitCost());
        part.setStockQuantity(request.getStockQuantity());
        part.setActive(true);

        partRepository.save(part);

        return mapToResponse(part);
    }

    @Override
    public List<PartResponse> getAllParts() {

        return partRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PartResponse getPartById(
            Long id) {

        Part part = partRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Part not found"));

        return mapToResponse(part);
    }

    @Override
    public PartResponse updatePart(
            Long id,
            UpdatePartRequest request) {

        Part part = partRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Part not found"));

        part.setPartCode(request.getPartCode());
        part.setPartName(request.getPartName());
        part.setDescription(request.getDescription());
        part.setUnitCost(request.getUnitCost());
        part.setStockQuantity(request.getStockQuantity());
        part.setActive(request.isActive());

        partRepository.save(part);

        return mapToResponse(part);
    }

    @Override
    public void deletePart(
            Long id) {

        Part part = partRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Part not found"));

        partRepository.delete(part);
    }

    private PartResponse mapToResponse(
            Part part) {

        return PartResponse.builder()
                .id(part.getId())
                .partCode(part.getPartCode())
                .partName(part.getPartName())
                .description(part.getDescription())
                .unitCost(part.getUnitCost())
                .stockQuantity(part.getStockQuantity())
                .active(part.isActive())
                .createdAt(part.getCreatedAt())
                .build();
    }
}