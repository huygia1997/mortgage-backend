package com.morgage.repository;

import com.morgage.model.Address;
import com.morgage.model.HasCategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HasCategoryItemRepository extends JpaRepository<HasCategoryItem, Integer> {



    List<HasCategoryItem> findHasCategoryItemsByIdCategoryItem(int cateId);
    List<HasCategoryItem> findAllByIdShop(int idShop);
    HasCategoryItem findById(int id);
}
