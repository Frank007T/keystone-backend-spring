package com.keystone.delivery_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keystone.delivery_service.entity.WorkOrder;
import com.keystone.delivery_service.enums.WorkOrderStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

    long countByStatus(WorkOrderStatus status);
    long countByTechnicianId(Long technicianId);

    long countByTechnicianIdAndStatus(
            Long technicianId,
            WorkOrderStatus status);
    long countByCustomerId(Long customerId);
    
  

    List<WorkOrder> findByCreatedAtBetween(
            LocalDate startDate,
            LocalDate endDate);

}