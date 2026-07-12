package com.keystone.delivery_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.delivery_service.entity.PartUsage;

public interface PartUsageRepository extends JpaRepository<PartUsage, Long> {

}