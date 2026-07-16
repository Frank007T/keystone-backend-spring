package com.keystone.delivery_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.keystone.delivery_service.dto.request.CreateStockMovementRequest;
import com.keystone.delivery_service.dto.response.StockMovementResponse;
import com.keystone.delivery_service.service.StockMovementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stock-movements")
@Validated
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(
            StockMovementService stockMovementService) {

        this.stockMovementService = stockMovementService;
    }

    @PostMapping
    public ResponseEntity<StockMovementResponse> createStockMovement(
            @Valid @RequestBody CreateStockMovementRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(stockMovementService.createStockMovement(request));
    }

    @GetMapping("/part/{partId}")
    public ResponseEntity<List<StockMovementResponse>> getByPart(
            @PathVariable Long partId) {

        return ResponseEntity.ok(
                stockMovementService.getByPart(partId));
    }
}