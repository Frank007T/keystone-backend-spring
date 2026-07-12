package com.keystone.delivery_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.delivery_service.entity.Site;

public interface SiteRepository extends JpaRepository<Site, Long> {

}