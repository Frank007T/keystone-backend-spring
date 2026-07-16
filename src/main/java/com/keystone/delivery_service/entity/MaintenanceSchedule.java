package com.keystone.delivery_service.entity;

import java.time.LocalDate;

import com.keystone.delivery_service.enums.MaintenanceFrequency;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "maintenance_schedules")
public class MaintenanceSchedule extends BaseEntity {

    @Column(nullable = false)
    private String scheduleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceFrequency frequency;

    @Column(nullable = false)
    private LocalDate nextMaintenanceDate;

    @Column(nullable = false)
    private boolean active = true;
}