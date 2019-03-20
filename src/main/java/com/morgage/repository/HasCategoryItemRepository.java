package com.morgage.repository;

import com.morgage.model.HasCategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HasCategoryItemRepository extends JpaRepository<HasCategoryItem, Integer> {
    List<HasCategoryItem> findHasCategoryItemsByIdCategoryItem(int cateId);
    List<HasCategoryItem> findAllByIdShop(int idShop);

}
