package com.morgage.repository;

import com.morgage.model.Shop;
import org.springframework.data.domain.Pageable;
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

    String SEARCH_NEARBY_QUERY =
            " SELECT *," +
                    " 6371 * 2 * ATAN2(SQRT(POWER(SIN(RADIANS(dest.latitude - :lat) / 2),2) + COS(RADIANS(:lat)) * COS(RADIANS(dest.latitude)) * POWER(SIN(RADIANS(dest.longtitude - :lng) / 2),2)), SQRT(1 - POWER(SIN(RADIANS(dest.latitude - :lat) / 2),2) + COS(RADIANS(:lat)) * COS(RADIANS(dest.latitude)) * POWER(SIN(RADIANS(dest.longtitude - :lng) / 2),2))) AS distance" +
                    " FROM shop sho join address dest on sho.address_id" +
                    " having distance < 5" +
                    " ORDER BY distance desc;";

    @Query(value = SEARCH_NEARBY_QUERY, nativeQuery = true)
    List<Shop> searchNearby(@Param("lat") Float input, @Param("lng") Float lng);

    List<Shop> findAllByShopNameContaining(String name);

    Shop findById(int id);

    Shop findShopByAddressId(int addressId);

    Shop findShopById(int shopId);

    Shop findByAccountId(int id);

    String distanceSelect = "(6371 * 2 * ATAN2(SQRT(POWER(SIN(RADIANS(dest.latitude - :lat) / 2),2) + COS(RADIANS(:lat)) * COS(RADIANS(dest.latitude)) * POWER(SIN(RADIANS(dest.longtitude - :lng) / 2),2)), SQRT(1 - POWER(SIN(RADIANS(dest.latitude - :lat) / 2),2) + COS(RADIANS(:lat)) * COS(RADIANS(dest.latitude)) * POWER(SIN(RADIANS(dest.longtitude - :lng) / 2),2))))";
    String rateSelect = "sho.rating";
    String viewSelect = "sho.view_count";
    String favoriteSelect = "sho.favorite_count";

    String countPoint = "(0.4 * (0.2 * " + viewSelect + " + 01 * " + favoriteSelect + " + 0.7 * 10 * " + rateSelect + ") + 0.6 * (4.8 - " + distanceSelect + "))";

    String countPointWithoutDistance = "(0.4 * (0.2 * " + viewSelect + " + 01 * " + favoriteSelect + " + 0.7 * 10 * " + rateSelect + "))";


    // 0.4 x (0.182 x view + 0.091 x like + 0.727 x 10 x rate) + 0.6 x (2.805 - dist)
    String query = "select *, " + countPoint + " as point_s"
            + " from shop sho join address dest on sho.address_id = dest.id "
            + "order by point_s desc";

    String queryWithoutDistance = "select *, " + countPointWithoutDistance + " as point_s"
            + " from shop sho join address dest on sho.address_id = dest.id "
            + "order by point_s desc";


    @Query(value = query, nativeQuery = true)
    List<Shop> suggestShop(@Param("lat") Float input, @Param("lng") Float lng, Pageable pageable);

    @Query(value = queryWithoutDistance, nativeQuery = true)
    List<Shop> suggestShopWithoutDistance(Pageable pageable);

    String SHOP_FILTER_QUERY = "SELECT DISTINCT sho.id, sho.shop_name, sho.phone_number, sho.facebook, sho.email, sho.status, sho.rating, sho.policy, sho.account_id, sho.address_id, sho.view_count, sho.avatar_url, sho.favorite_count from shop sho join has_category_item f on sho.id = f.id_shop join address addr on sho.address_id = addr.id join district dist on dist.id = addr.district_id where f.id_category_item = :cateId and dist.id = :disId";

    @Query(value = SHOP_FILTER_QUERY, nativeQuery = true)
    List<Shop> getShopFilter(@Param("cateId") int cateId, @Param("disId") int disId, Pageable pageable);




    String SHOP_FILTER_QUERY_WITHOUT_DISID = "SELECT DISTINCT sho.id, sho.shop_name, sho.phone_number, sho.facebook, sho.email, sho.status, sho.rating, sho.policy, sho.account_id, sho.address_id, sho.view_count, sho.avatar_url, sho.favorite_count from  shop sho  join has_category_item f on sho.id = f.id_shop join address addr on sho.address_id = addr.id join district dist on dist.id = addr.district_id where f.id_category_item = :cateId ";

    @Query(value = SHOP_FILTER_QUERY_WITHOUT_DISID, nativeQuery = true)
    List<Shop> getShopFilterWithoutDisId(@Param("cateId") int cateId, Pageable pageable);

    String SHOP_FILTE_QUERY_WITHOUT_CATEID = "SELECT DISTINCT sho.id, sho.shop_name, sho.phone_number, sho.facebook, sho.email, sho.status, sho.rating, sho.policy, sho.account_id, sho.address_id, sho.view_count, sho.avatar_url, sho.favorite_count from shop sho  join has_category_item f  on sho.id = f.id_shop join address addr on sho.address_id = addr.id join district dist on dist.id = addr.district_id where dist.id = :disId";

    @Query(value = SHOP_FILTE_QUERY_WITHOUT_CATEID, nativeQuery = true)
    List<Shop> getShopFilterWithoutCateId(@Param("disId") int disId, Pageable pageable);
    String SHOP_FILTER_QUERY_WITHOUT_DISID_CATEID = "SELECT DISTINCT sho.id, sho.shop_name, sho.phone_number, sho.facebook, sho.email, sho.status, sho.rating, sho.policy, sho.account_id, sho.address_id, sho.view_count, sho.avatar_url, sho.favorite_count from  shop sho  join has_category_item f on sho.id = f.id_shop join address addr on sho.address_id = addr.id join district dist on dist.id = addr.district_id ";
    @Query(value = SHOP_FILTER_QUERY_WITHOUT_DISID_CATEID, nativeQuery = true)
    List<Shop> getAllShop(Pageable pageable);

    @Query(value = "select s from Shop s")
    List<Shop> paging(Pageable pageable);

    List<Shop> findAllByStatus(int status);

    //for map view
    @Query(value = SHOP_FILTER_QUERY, nativeQuery = true)
    List<Shop> getShopMapFilter(@Param("cateId") int cateId, @Param("disId") int disId);

    @Query(value = SHOP_FILTER_QUERY_WITHOUT_DISID, nativeQuery = true)
    List<Shop> getShopMapFilterWithoutDisId(@Param("cateId") int cateId);

    @Query(value = SHOP_FILTE_QUERY_WITHOUT_CATEID, nativeQuery = true)
    List<Shop> getShopMapFilterWithoutCateId(@Param("disId") int disId);

    @Query(value = SHOP_FILTER_QUERY_WITHOUT_DISID_CATEID, nativeQuery = true)
    List<Shop> getAllMapShop();
}
