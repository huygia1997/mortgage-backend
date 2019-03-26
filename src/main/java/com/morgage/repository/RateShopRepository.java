package com.morgage.repository;

import com.morgage.model.RateShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateShopRepository extends JpaRepository<RateShop, Integer> {
    RateShop findByShop_IdAndPawnee_Id(int shopId, int pawneeId);
    List<RateShop> findAllByShop_Id(int shopId);
}
