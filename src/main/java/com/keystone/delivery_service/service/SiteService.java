package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.request.CreateSiteRequest;
import com.keystone.delivery_service.dto.response.SiteResponse;

public interface SiteService {

    SiteResponse createSite(
            CreateSiteRequest request);

    List<SiteResponse> getAllSites();

    SiteResponse getSiteById(
            Long id);

    void deleteSite(
            Long id);

}