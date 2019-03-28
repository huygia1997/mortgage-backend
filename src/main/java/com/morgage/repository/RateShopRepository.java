package com.morgage.repository;

import com.morgage.model.RateShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateShopRepository extends JpaRepository<RateShop, Integer> {
    RateShop findByShopIdAndPawneeId(int shopId, int pawneeId);
    List<RateShop> findAllByShopId(int shopId);
}
