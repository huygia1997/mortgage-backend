package com.morgage.service;

import com.morgage.common.Const;
import com.morgage.model.*;
import com.morgage.model.data.SaleItemDetail;
import com.morgage.repository.*;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleItemService {
    private final SaleItemRepository saleItemRepository;
    private final PawneeService pawneeService;
    private final PawneeFavoriteItemRepository pawneeFavoriteItemRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final PictureRepository pictureRepository;

    public SaleItemService(SaleItemRepository saleItemRepository, PawneeService pawneeService, PawneeFavoriteItemRepository pawneeFavoriteItemRepository, CategoryRepository categoryRepository, TransactionRepository transactionRepository, PictureRepository pictureRepository) {
        this.saleItemRepository = saleItemRepository;
        this.pawneeService = pawneeService;
        this.pawneeFavoriteItemRepository = pawneeFavoriteItemRepository;
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

    public SaleItemDetail getSaleItemInformation(int itemId, Integer userId) {
        SaleItem saleItem = saleItemRepository.findById(itemId);
        if (saleItem != null) {
            saleItem.setViewCount(saleItem.getViewCount() + 1);
            saleItemRepository.save(saleItem);
            SaleItemDetail saleItemDetail = new SaleItemDetail();
            saleItemDetail.setStatus(saleItem.getStatus());
            Category category = categoryRepository.findCategoryById(saleItem.getCategoryId());
            saleItemDetail.setCategoryId(saleItem.getCategoryId());
            saleItemDetail.setCategoryImgUrl(category.getIconUrl());
            saleItemDetail.setCategoryName(category.getCategoryName());
            saleItemDetail.setId(saleItem.getId());
            saleItemDetail.setName(saleItem.getItemName());
            saleItemDetail.setPrice(saleItem.getPrice());
            saleItemDetail.setView(saleItem.getViewCount());
            if (userId != null) {
                Pawnee pawnee = pawneeService.getPawneeByAccountId(userId);
                if (pawneeFavoriteItemRepository.findByPawnerIdAndItemId(pawnee.getId(), itemId) != null) {
                    saleItemDetail.setCheckFavorite(true);
                } else saleItemDetail.setCheckFavorite(false);
            } else saleItemDetail.setCheckFavorite(false);
            Transaction transaction = transactionRepository.findTransactionById(saleItem.getTransactionId());
            saleItemDetail.setShopId(transaction.getShopId());
            List<Picture> listPicture = pictureRepository.findAllByObjectIdAndStatus(itemId, Const.PICTURE_STATUS.ITEM);
            List<String> listUrlPicture = new ArrayList<>();
            if (listPicture != null) {
                for (Picture picture : listPicture) {
                    listUrlPicture.add(picture.getPictureUrl());
                }
            }
            saleItemDetail.setPictureURL(listUrlPicture);
            return saleItemDetail;
        } else return null;
    }

    public Boolean followItem(int itemId, int userId) {
        Pawnee pawnee = pawneeService.getPawneeByAccountId(userId);
        if (pawneeFavoriteItemRepository.findByPawnerIdAndItemId(pawnee.getId(), itemId) != null) {
            return false;
        } else {
            SaleItem saleItem = saleItemRepository.findById(itemId);
            saleItem.setFavoriteCount(saleItem.getFavoriteCount() + 1);
            saleItemRepository.save(saleItem);
            PawneeFavoriteItem pawneeFavoriteItem = new PawneeFavoriteItem();
            pawneeFavoriteItem.setPawnerId(pawnee.getId());
            pawneeFavoriteItem.setItemId(itemId);
            pawneeFavoriteItemRepository.saveAndFlush(pawneeFavoriteItem);
            return true;
        }
    }

    public Boolean unFollowItem(int itemId, int userId) {
        Pawnee pawnee = pawneeService.getPawneeByAccountId(userId);
        PawneeFavoriteItem pawneeFavoriteItem = pawneeFavoriteItemRepository.findByPawnerIdAndItemId(pawnee.getId(), itemId);
        if (pawneeFavoriteItem == null) {
            return false;
        } else {
            SaleItem saleItem = saleItemRepository.findById(itemId);
            saleItem.setFavoriteCount(saleItem.getFavoriteCount() - 1);
            saleItemRepository.save(saleItem);
            pawneeFavoriteItemRepository.delete(pawneeFavoriteItem);
            return true;
        }
    }

    public List<PawneeFavoriteItem> findAllFavoeiteByItemId(int itemId) {
        List<PawneeFavoriteItem> rs = pawneeFavoriteItemRepository.findAllByItemId(itemId);
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

    public List<SaleItem> getItemList(Pageable pageable) {
        return saleItemRepository.getAllItemPaging(pageable);
    }

    public List<SaleItem> getItemListByCategoryId(int categoryId, Pageable pageable) {
        return saleItemRepository.findAllByCategoryId(pageable, categoryId);
    }

    public List<SaleItem> suggestItem(Float lat, Float lng, Pageable pageable) {
        return saleItemRepository.suggestItem(lat, lng, pageable);
    }

    public List<SaleItem> getItemListByShop(int shopId) {
        return saleItemRepository.getItemByShop(shopId);
    }


    public List<SaleItem> suggestItemWithoutDistance(Pageable pageable) {
        return saleItemRepository.suggestItemWithoutDistance(pageable);
    }

    public List<SaleItem> searchByItemName(String searchValue) {
        List<SaleItem> listItem = saleItemRepository.findAllByItemNameContaining(searchValue);

        return listItem;
    }
}
