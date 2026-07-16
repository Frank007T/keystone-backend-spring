package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.request.CreatePartRequest;
import com.keystone.delivery_service.dto.request.UpdatePartRequest;
import com.keystone.delivery_service.dto.response.PartResponse;

public interface PartService {

    PartResponse createPart(
            CreatePartRequest request);

    List<PartResponse> getAllParts();

    PartResponse getPartById(
            Long id);

    PartResponse updatePart(
            Long id,
            UpdatePartRequest request);

    void deletePart(
            Long id);
}