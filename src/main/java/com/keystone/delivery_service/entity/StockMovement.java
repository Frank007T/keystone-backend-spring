package com.keystone.delivery_service.entity;

import java.time.LocalDateTime;

import com.keystone.delivery_service.enums.StockMovementType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stock_movements")
public class StockMovement extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", nullable = false)
    private Part part;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockMovementType movementType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 500)
    private String remarks;

    @Column(nullable = false)
    private LocalDateTime movementDate = LocalDateTime.now();
}