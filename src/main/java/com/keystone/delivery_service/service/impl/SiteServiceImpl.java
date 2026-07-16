package com.keystone.delivery_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.delivery_service.dto.request.CreateSiteRequest;
import com.keystone.delivery_service.dto.response.SiteResponse;
import com.keystone.delivery_service.entity.Customer;
import com.keystone.delivery_service.entity.Site;
import com.keystone.delivery_service.repository.CustomerRepository;
import com.keystone.delivery_service.repository.SiteRepository;
import com.keystone.delivery_service.service.SiteService;

@Service
@Transactional
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;
    private final CustomerRepository customerRepository;

    public SiteServiceImpl(
            SiteRepository siteRepository,
            CustomerRepository customerRepository) {

        this.siteRepository = siteRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public SiteResponse createSite(
            CreateSiteRequest request) {

        Customer customer = customerRepository.findById(
                request.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Site site = new Site();

        site.setSiteName(request.getSiteName());
        site.setAddress(request.getAddress());
        site.setCity(request.getCity());
        site.setState(request.getState());
        site.setPostalCode(request.getPostalCode());
        site.setCustomer(customer);

        siteRepository.save(site);

        return mapToResponse(site);
    }

    @Override
    public List<SiteResponse> getAllSites() {

        return siteRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SiteResponse getSiteById(
            Long id) {

        Site site = siteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Site not found"));

        return mapToResponse(site);
    }

    @Override
    public void deleteSite(
            Long id) {

        Site site = siteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Site not found"));

        siteRepository.delete(site);
    }

    private SiteResponse mapToResponse(
            Site site) {

        return SiteResponse.builder()
                .id(site.getId())
                .siteName(site.getSiteName())
                .address(site.getAddress())
                .city(site.getCity())
                .state(site.getState())
                .postalCode(site.getPostalCode())
                .customerId(site.getCustomer().getId())
                .customerName(site.getCustomer().getCustomerName())
                .build();
    }
}