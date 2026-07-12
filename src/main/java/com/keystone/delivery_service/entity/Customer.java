package com.keystone.delivery_service.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contactPerson;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(length = 500)
    private String address;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Site> sites = new ArrayList<>();

}