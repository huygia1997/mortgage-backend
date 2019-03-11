package com.morgage.repository;

import com.morgage.model.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, Integer> {
    CategoryItem findById(int id);
}
