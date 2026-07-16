package com.keystone.delivery_service.repository;

import java.time.LocalDate;
import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.delivery_service.entity.MaintenanceSchedule;

public interface MaintenanceScheduleRepository
        extends JpaRepository<MaintenanceSchedule, Long> {

    List<MaintenanceSchedule> findByNextMaintenanceDateLessThanEqual(
            LocalDate date);
    long countByCustomerId(Long customerId);
    
    List<MaintenanceSchedule> findByNextMaintenanceDateBetween(
            LocalDate startDate,
            LocalDate endDate);

}