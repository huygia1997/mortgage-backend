package com.morgage.repository;

import com.morgage.model.PawnerFavouriteShop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PawneeFavoriteShopRepository extends JpaRepository<PawnerFavouriteShop, Integer> {
    PawnerFavouriteShop findByShopIdAndPawnerId(int shopId, int pawneeId);
}
