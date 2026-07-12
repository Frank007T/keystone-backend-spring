package com.keystone.delivery_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.delivery_service.entity.WorkOrder;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

}