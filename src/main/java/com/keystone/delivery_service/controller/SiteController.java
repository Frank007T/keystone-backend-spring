package com.keystone.delivery_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.keystone.delivery_service.dto.request.CreateSiteRequest;
import com.keystone.delivery_service.dto.response.SiteResponse;
import com.keystone.delivery_service.service.SiteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sites")
@Validated
public class SiteController {

    private final SiteService siteService;

    public SiteController(
            SiteService siteService) {

        this.siteService = siteService;
    }

    @PostMapping
    public ResponseEntity<SiteResponse> createSite(
            @Valid @RequestBody CreateSiteRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(siteService.createSite(request));
    }

    @GetMapping
    public ResponseEntity<List<SiteResponse>> getAllSites() {

        return ResponseEntity.ok(
                siteService.getAllSites());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteResponse> getSiteById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                siteService.getSiteById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSite(
            @PathVariable Long id) {

        siteService.deleteSite(id);

        return ResponseEntity.ok(
                "Site deleted successfully.");
    }
}