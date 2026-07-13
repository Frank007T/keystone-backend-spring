package com.keystone.delivery_service.entity;

import java.time.LocalDateTime;

import com.keystone.delivery_service.enums.Priority;
import com.keystone.delivery_service.enums.WorkOrderStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "work_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrder extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String workOrderNumber;

    @Column(nullable = false)
    private String title;

    @Column(length = 3000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkOrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    private User technician;

    private LocalDateTime expectedCompletionDate;

    @PrePersist
    public void prePersist() {

        if (status == null) {
            status = WorkOrderStatus.NEW;
        }

        if (priority == null) {
            priority = Priority.MEDIUM;
        }
    }

}