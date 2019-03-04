package com.morgage.repository;

import com.morgage.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop,Integer> {
    String SEARCH_QUERY = "select * " +
            "from Shop sh " +
            "where sh.shopName like %:input%";

//    @Query("select * from Shop sh where sh.shopName like %:input%")
//    public List<Shop> searchShopByName(@Param("input") String input);

    List<Shop> findAllByShopNameContaining(String name);
}
