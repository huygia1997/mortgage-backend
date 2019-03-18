package com.morgage.service;

import com.morgage.common.Const;
import com.morgage.model.Pawner;
import com.morgage.model.PawnerFavoriteItem;
import com.morgage.model.SaleItem;
import com.morgage.model.Transaction;
import com.morgage.repository.PawnerFavoriteItemRepository;
import com.morgage.repository.SaleItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleItemService {
    private final SaleItemRepository saleItemRepository;
    private final PawnerService pawnerService;
    private final PawnerFavoriteItemRepository pawnerFavoriteItemRepository;

    public SaleItemService(SaleItemRepository saleItemRepository, PawnerService pawnerService, PawnerFavoriteItemRepository pawnerFavoriteItemRepository) {
        this.saleItemRepository = saleItemRepository;
        this.pawnerService = pawnerService;
        this.pawnerFavoriteItemRepository = pawnerFavoriteItemRepository;
    }

    public SaleItem publicItemForSale(Transaction transaction, int picId, int price, int status) {
        SaleItem item = new SaleItem();
        item.setStatus(status);
        item.setCategoryId(transaction.getCategoryItemId());
        item.setItemName(transaction.getItemName());
        item.setPictureId(picId);
        item.setTransactionId(transaction.getId());
        item.setViewCount(0);
        item.setPrice(price);
        return saleItemRepository.saveAndFlush(item);
    }

    public SaleItem getSaleItemInformation(int itemId) {
        SaleItem saleItem = saleItemRepository.findById(itemId);
        if (saleItem != null) {
            saleItem.setViewCount(saleItem.getViewCount() + 1);
            saleItemRepository.save(saleItem);
            return saleItem;
        } else return null;
    }

    public Boolean followItem(int itemId, int userId) {
        Pawner pawner = pawnerService.getPawnerByAccountId(userId);
        if (pawnerFavoriteItemRepository.findByPawnerIdAndItemId(pawner.getId(), itemId) != null) {
            return false;
        } else {
            PawnerFavoriteItem pawnerFavoriteItem = new PawnerFavoriteItem();
            pawnerFavoriteItem.setPawnerId(pawner.getId());
            pawnerFavoriteItem.setItemId(itemId);
            pawnerFavoriteItemRepository.saveAndFlush(pawnerFavoriteItem);
            return true;
        }
    }

    public Boolean unFollowItem(int itemId, int userId) {
        Pawner pawner = pawnerService.getPawnerByAccountId(userId);
        PawnerFavoriteItem pawnerFavoriteItem = pawnerFavoriteItemRepository.findByPawnerIdAndItemId(pawner.getId(), itemId);
        if (pawnerFavoriteItem == null) {
            return false;
        } else {
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
    public SaleItem findItemById(int id){
        return saleItemRepository.findById(id);
    }
}
