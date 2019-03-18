package com.morgage.repository;

import com.morgage.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem,Integer> {
    SaleItem findById(int id);
}
