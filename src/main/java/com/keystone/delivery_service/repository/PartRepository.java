package com.keystone.delivery_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keystone.delivery_service.entity.Part;

import java.util.List;


@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

    boolean existsByPartCode(String partCode);
    
    long countByActiveTrue();

    long countByActiveFalse();

    long countByStockQuantityLessThanEqual(Integer quantity);

    long countByStockQuantity(Integer quantity);
    
    List<Part> findByStockQuantityLessThanEqual(Integer quantity);

}