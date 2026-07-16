package com.keystone.delivery_service.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.keystone.delivery_service.entity.TimeLog;

public interface TimeLogRepository
        extends JpaRepository<TimeLog, Long> {

    @Query("""
        SELECT COALESCE(SUM(t.hoursWorked),0)
        FROM TimeLog t
        WHERE t.technician.id = :technicianId
    """)
    BigDecimal getTotalHoursWorked(
            Long technicianId);

}