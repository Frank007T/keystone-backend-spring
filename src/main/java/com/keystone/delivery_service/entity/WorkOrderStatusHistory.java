package com.keystone.delivery_service.entity;

import java.time.LocalDateTime;

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
@Table(name = "work_order_status_history")
public class WorkOrderStatusHistory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkOrderStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkOrderStatus newStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by")
    private User changedBy;

    @Column(length = 500)
    private String remarks;

    @Column(nullable = false)
    private LocalDateTime changedAt = LocalDateTime.now();
}