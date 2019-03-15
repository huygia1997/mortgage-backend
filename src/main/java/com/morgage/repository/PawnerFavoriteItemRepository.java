package com.morgage.repository;

import com.morgage.model.PawnerFavoriteItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PawnerFavoriteItemRepository extends JpaRepository<PawnerFavoriteItem, Integer> {
    PawnerFavoriteItem findByPawnerIdAndItemId(int pawnerId, int itemId);

    List<PawnerFavoriteItem> findAllByItemId(int itemId);
}
