package com.morgage.service;

import com.morgage.model.Pawnee;
import com.morgage.model.PawnerFavoriteItem;
import com.morgage.model.SaleItem;
import com.morgage.model.Transaction;
import com.morgage.repository.*;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleItemService {
    private final SaleItemRepository saleItemRepository;
    private final PawneeService pawneeService;
    private final PawnerFavoriteItemRepository pawnerFavoriteItemRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final PictureRepository pictureRepository;

    public SaleItemService(SaleItemRepository saleItemRepository, PawneeService pawneeService, PawnerFavoriteItemRepository pawnerFavoriteItemRepository, CategoryRepository categoryRepository, TransactionRepository transactionRepository, PictureRepository pictureRepository) {
        this.saleItemRepository = saleItemRepository;
        this.pawneeService = pawneeService;
        this.pawnerFavoriteItemRepository = pawnerFavoriteItemRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
        this.pictureRepository = pictureRepository;
    }

    public SaleItem publicItemForSale(Transaction transaction, String picUrl, int price, int status, Timestamp liquidationDate) {
        SaleItem item = new SaleItem();
        item.setStatus(status);
        item.setCategoryId(transaction.getCategoryItemId());
        item.setItemName(transaction.getItemName());
        item.setPicUrl(picUrl);
        item.setTransactionId(transaction.getId());
        item.setViewCount(0);
        item.setPrice(price);
        item.setFavoriteCount(0);
        item.setLiquidationDate(liquidationDate);
        return saleItemRepository.saveAndFlush(item);
    }

    public SaleItem getSaleItemInformation(int itemId, Integer userId) {
        SaleItem saleItem = saleItemRepository.findById(itemId);
        if (saleItem != null) {
//            if (userId != null) {
//                Pawnee pawnee = pawneeService.getPawneeByAccountId(userId);
//                if (pawnerFavoriteItemRepository.findByPawnerIdAndItemId(pawnee.getId(), itemId) != null) {
//                    saleItem.setCheckFavorite(true);
//                } else saleItem.setCheckFavorite(false);
//            }
            saleItem.setViewCount(saleItem.getViewCount() + 1);
            saleItemRepository.save(saleItem);
            return saleItem;
        } else return null;
    }

    public Boolean followItem(int itemId, int userId) {
        Pawnee pawnee = pawneeService.getPawneeByAccountId(userId);
        if (pawnerFavoriteItemRepository.findByPawnerIdAndItemId(pawnee.getId(), itemId) != null) {
            return false;
        } else {
            SaleItem saleItem = saleItemRepository.findById(itemId);
            saleItem.setFavoriteCount(saleItem.getFavoriteCount() + 1);
            saleItemRepository.save(saleItem);
            PawnerFavoriteItem pawnerFavoriteItem = new PawnerFavoriteItem();
            pawnerFavoriteItem.setPawnerId(pawnee.getId());
            pawnerFavoriteItem.setItemId(itemId);
            pawnerFavoriteItemRepository.saveAndFlush(pawnerFavoriteItem);
            return true;
        }
    }

    public Boolean unFollowItem(int itemId, int userId) {
        Pawnee pawnee = pawneeService.getPawneeByAccountId(userId);
        PawnerFavoriteItem pawnerFavoriteItem = pawnerFavoriteItemRepository.findByPawnerIdAndItemId(pawnee.getId(), itemId);
        if (pawnerFavoriteItem == null) {
            return false;
        } else {
            SaleItem saleItem = saleItemRepository.findById(itemId);
            saleItem.setFavoriteCount(saleItem.getFavoriteCount() - 1);
            saleItemRepository.save(saleItem);
            pawnerFavoriteItemRepository.delete(pawnerFavoriteItem);
            return true;
        }
    }

    public List<PawnerFavoriteItem> findAllFavoeiteByItemId(int itemId) {
        List<PawnerFavoriteItem> rs = pawnerFavoriteItemRepository.findAllByItemId(itemId);
        return rs;
    }

    public SaleItem changeStatusItem(int itemId, int status) {
        SaleItem saleItem = saleItemRepository.findById(itemId);
        if (saleItem != null && saleItem.getStatus() != status) {
            saleItem.setStatus(status);
            saleItemRepository.save(saleItem);
            return saleItem;
        } else return null;
    }

    public SaleItem findItemById(int id) {
        return saleItemRepository.findById(id);
    }

    public List<SaleItem> getItemList() {
        return saleItemRepository.findAll();
    }

    public List<SaleItem> suggestItem(Float lat, Float lng) {
            return saleItemRepository.suggestItem(lat, lng);
    }
    public List<SaleItem> suggestItemWithoutDistance() {
        return saleItemRepository.suggestItemWithoutDistance();
    }
}
