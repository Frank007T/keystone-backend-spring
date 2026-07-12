package com.keystone.delivery_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.delivery_service.entity.WorkOrderStatusHistory;

public interface WorkOrderStatusHistoryRepository extends JpaRepository<WorkOrderStatusHistory, Long> {

}