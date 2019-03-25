package com.morgage.repository;

import com.morgage.model.SaleItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem, Integer> {
    SaleItem findById(int id);


    String distanceSelect = "(6371 * 2 * ATAN2(SQRT(POWER(SIN(RADIANS(dest.latitude - :lat) / 2),2) + COS(RADIANS(:lat)) * COS(RADIANS(dest.latitude)) * POWER(SIN(RADIANS(dest.longtitude - :lng) / 2),2)), SQRT(1 - POWER(SIN(RADIANS(dest.latitude - :lat) / 2),2) + COS(RADIANS(:lat)) * COS(RADIANS(dest.latitude)) * POWER(SIN(RADIANS(dest.longtitude - :lng) / 2),2))))";
    String timeSelect = "(DATEDIFF(NOW(), sal.liquidation_date) + 1)";
    String viewSelect = "sal.view_count";
    //    String favoriteSelect = "(select count(*) from  pawner_favorite_item pfi where pfi.item_id = sal.id)";
    String favoriteSelect = "sal.favorite_count";

    // 0.4 x (0.4 x view + 0.6 x like ) / time + 0.6 x ( tbinh - dist )
    String countPoint = "(0.4 * (0.4 * " + viewSelect + " + 0.6 * " + favoriteSelect + " ) / " + timeSelect + " + 0.6 * ( 8.2 - " + distanceSelect + " ) ) as point_i";
    String countPointWithoutDistance = "((0.4 * sal.view_count + 0.6 * " + favoriteSelect + " ) / " + timeSelect + " ) as point_i";

    String query = "select *, " + countPoint
            + " from sales_item sal join transaction trans on sal.transaction_id = trans.id join shop sho on sho.id = trans.shop_id join address dest on dest.id = sho.address_id "
            + " order by point_i DESC LIMIT 10";

    String queryWithoutDistance = "select *, " + countPointWithoutDistance
            + " from sales_item sal join transaction trans on sal.transaction_id = trans.id join shop sho on sho.id = trans.shop_id join address dest on dest.id = sho.address_id "
            + " order by point_i DESC LIMIT 10";

    @Query(value = query, nativeQuery = true)
    List<SaleItem> suggestItem(@Param("lat") Float input, @Param("lng") Float lng);

    @Query(value = queryWithoutDistance, nativeQuery = true)
    List<SaleItem> suggestItemWithoutDistance();

    @Query(value = "SELECT c FROM SaleItem c")
    List<SaleItem> getAllItemPaging(Pageable pageable);
}
