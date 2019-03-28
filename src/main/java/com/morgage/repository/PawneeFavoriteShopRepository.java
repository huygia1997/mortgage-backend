package com.morgage.repository;

import com.morgage.model.PawneeFavouriteShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PawneeFavoriteShopRepository extends JpaRepository<PawneeFavouriteShop, Integer> {
    PawneeFavouriteShop findByShopIdAndPawnerId(int shopId, int pawneeId);

    List<PawneeFavouriteShop> findAllByPawnerId(int pawneeId);
}
