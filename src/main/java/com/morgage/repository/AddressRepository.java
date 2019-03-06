package com.morgage.repository;

import com.morgage.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findAddressById(int id);

//    String SEARCH_NEARBY2_QUERY =
//            " SELECT *," +
//                    " 111.111 * DEGREES(ACOS(LEAST(COS(RADIANS(:lat)) * COS(RADIANS(dest.latitude)) * COS(RADIANS(:lng - dest.longtitude)) + SIN(RADIANS(:lat)) * SIN(RADIANS(dest.latitude)), 1.0))) AS distance" +
//                    " FROM address dest" +
//                    " having distance < 10" +
//                    " ORDER BY distance desc;";
    String SEARCH_NEARBY_QUERY =
            " SELECT *," +
                    " 6371 * 2 * ATAN2(SQRT(POWER(SIN(RADIANS(dest.latitude - :lat) / 2),2) + COS(RADIANS(:lat)) * COS(RADIANS(dest.latitude)) * POWER(SIN(RADIANS(dest.longtitude - :lng) / 2),2)), SQRT(1 - POWER(SIN(RADIANS(dest.latitude - :lat) / 2),2) + COS(RADIANS(:lat)) * COS(RADIANS(dest.latitude)) * POWER(SIN(RADIANS(dest.longtitude - :lng) / 2),2))) AS distance" +
                    " FROM address dest" +
                    " having distance < 1" +
                    " ORDER BY distance desc;";

    @Query(value = SEARCH_NEARBY_QUERY, nativeQuery = true)
    List<Address> searchNearby(@Param("lat") Float input, @Param("lng") Float lng);




}
