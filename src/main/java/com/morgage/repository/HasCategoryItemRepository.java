package com.morgage.repository;

import com.morgage.model.Address;
import com.morgage.model.HasCategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HasCategoryItemRepository extends JpaRepository<HasCategoryItem, Integer> {

//    String SHOP_INFO_QUERY = "SELECT DISTINCT id_category_item from has_category_item f where f.id_shop = :id";
//    @Query(value = SHOP_INFO_QUERY, nativeQuery = true)
//    List<HasCategoryItem> getCategoryItemId(@Param("id") int id);

    List<HasCategoryItem> findHasCategoryItemsByIdCategoryItem(int cateId);
    List<HasCategoryItem> findAllByIdShop(int idShop);
    HasCategoryItem findById(int id);
}
