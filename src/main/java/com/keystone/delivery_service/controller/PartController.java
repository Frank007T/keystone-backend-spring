package com.keystone.delivery_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.keystone.delivery_service.dto.request.CreatePartRequest;
import com.keystone.delivery_service.dto.request.UpdatePartRequest;
import com.keystone.delivery_service.dto.response.PartResponse;
import com.keystone.delivery_service.service.PartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/parts")
@Validated
public class PartController {

    private final PartService partService;

    public PartController(
            PartService partService) {

        this.partService = partService;
    }

    @PostMapping
    public ResponseEntity<PartResponse> createPart(
            @Valid @RequestBody CreatePartRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(partService.createPart(request));
    }

    @GetMapping
    public ResponseEntity<List<PartResponse>> getAllParts() {

        return ResponseEntity.ok(
                partService.getAllParts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartResponse> getPartById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                partService.getPartById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartResponse> updatePart(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePartRequest request) {

        return ResponseEntity.ok(
                partService.updatePart(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePart(
            @PathVariable Long id) {

        partService.deletePart(id);

        return ResponseEntity.noContent().build();
    }
}