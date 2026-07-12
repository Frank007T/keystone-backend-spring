package com.keystone.delivery_service.entity;

import java.time.LocalDate;

import com.keystone.delivery_service.enums.Priority;
import com.keystone.delivery_service.enums.WorkOrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "work_orders")
public class WorkOrder extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String workOrderNumber;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkOrderStatus status = WorkOrderStatus.NEW;

    @Column(nullable = false)
    private LocalDate dueDate;

    // Customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Site
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    // Assigned Technician
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    private User assignedTechnician;
}