package com.keystone.delivery_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.delivery_service.entity.Part;

public interface PartRepository extends JpaRepository<Part, Long> {

}