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

    // 0.519 x (0.117 x view + 0.883 x follow ) / time + 0.481 x ( 8.88 - dist )
    String countPoint = "(0.4 * (0.4 * " + viewSelect + " + 0.6 * " + favoriteSelect + " ) / " + timeSelect + " + 0.6 * ( 8.2 - " + distanceSelect + " ) ) as point_i";
    String countPointWithoutDistance = "((0.4 * sal.view_count + 0.6 * " + favoriteSelect + " ) / " + timeSelect + " ) as point_i";

    String query = "select *, " + countPoint
            + " from sales_item sal join transaction trans on sal.transaction_id = trans.id join shop sho on sho.id = trans.shop_id join address dest on dest.id = sho.address_id "
            + " order by point_i DESC LIMIT 10";



    String queryWithoutDistance = "select *, " + countPointWithoutDistance
            + " from sales_item sal join transaction trans on sal.transaction_id = trans.id join shop sho on sho.id = trans.shop_id join address dest on dest.id = sho.address_id "
            + " order by point_i DESC";

    String queryGetItemByShop = "Select * from sales_item si join transaction trans on si.transaction_id = trans.id join shop sho on sho.id = trans.shop_id where sho.id = :shopId";

    @Query(value = query, nativeQuery = true)
    List<SaleItem> suggestItem(@Param("lat") Float input, @Param("lng") Float lng, Pageable pageable);

    @Query(value = queryWithoutDistance, nativeQuery = true)
    List<SaleItem> suggestItemWithoutDistance(Pageable pageable);

    @Query(value = "SELECT c FROM SaleItem c")
    List<SaleItem> getAllItemPaging(Pageable pageable);

    List<SaleItem> findAllByCategoryId(Pageable pageable, int categoryId);
    @Query(value = queryGetItemByShop, nativeQuery = true)
    List<SaleItem> getItemByShop(@Param("shopId") int shopId);

    List<SaleItem> findAllByItemNameContaining(String searchValue);

}
