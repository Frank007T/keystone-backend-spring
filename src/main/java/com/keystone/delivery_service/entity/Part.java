package com.keystone.delivery_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parts")
public class Part extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String partCode;

    @Column(nullable = false)
    private String partName;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitCost;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private boolean active = true;
}