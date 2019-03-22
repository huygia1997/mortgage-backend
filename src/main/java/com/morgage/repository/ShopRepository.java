package com.morgage.repository;

import com.morgage.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    String SEARCH_QUERY = "select * " +
            "from Shop sh " +
            "where sh.shopName like %:input%";

//    @Query("select * from Shop sh where sh.shopName like %:input%")
//    public List<Shop> searchShopByName(@Param("input") String input);

    List<Shop> findAllByShopNameContaining(String name);

    Shop findById(int id);

    Shop findShopByAddressId(int addressId);

    Shop findShopById(int shopId);

    Shop findByAccountId(int id);

    String distanceSelect = "(6371 * 2 * ATAN2(SQRT(POWER(SIN(RADIANS(dest.latitude - :lat) / 2),2) + COS(RADIANS(:lat)) * COS(RADIANS(dest.latitude)) * POWER(SIN(RADIANS(dest.longtitude - :lng) / 2),2)), SQRT(1 - POWER(SIN(RADIANS(dest.latitude - :lat) / 2),2) + COS(RADIANS(:lat)) * COS(RADIANS(dest.latitude)) * POWER(SIN(RADIANS(dest.longtitude - :lng) / 2),2))))";
    String rateSelect = "sho.rating";
    String viewSelect = "sho.view_count";
    String favoriteSelect = "sho.favorite_count";

    String countPoint = "(0.4 * (0.2 * " + viewSelect + " + 01 * " + favoriteSelect + " + 0.7 * 10 * " + rateSelect + ") + 0.6 * (4.8 - "+ distanceSelect +"))";

    String countPointWithoutDistance = "(0.4 * (0.2 * "+viewSelect+" + 01 * "+favoriteSelect+" + 0.7 * 10 * "+rateSelect+"))";


    // 0.4 x (0.2 x view + 01 x like + 0.7 x 10 x rate) + 0.6 x (4.8 - dist)
    String query = "select *, " + countPoint +" as point_s"
            + " from shop sho join address dest on sho.address_id = dest.id "
            + "order by point_s desc limit 10";

    String queryWithoutDistance = "select *, " + countPointWithoutDistance +" as point_s"
            + " from shop sho join address dest on sho.address_id = dest.id "
            + "order by point_s desc limit 10";

    @Query(value = query, nativeQuery = true)
    List<Shop> suggestShop(@Param("lat") Float input, @Param("lng") Float lng);

    @Query(value = queryWithoutDistance, nativeQuery = true)
    List<Shop> suggestShopWithoutDistance();
}
