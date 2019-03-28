package com.morgage.repository;

import com.morgage.model.PawneeFavoriteItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PawneeFavoriteItemRepository extends JpaRepository<PawneeFavoriteItem, Integer> {
    PawneeFavoriteItem findByPawnerIdAndItemId(int pawnerId, int itemId);

    List<PawneeFavoriteItem> findAllByItemId(int itemId);
    List<PawneeFavoriteItem> findAllByPawnerId(int itemId);

}
