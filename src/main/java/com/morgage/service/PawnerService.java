package com.morgage.service;

import com.morgage.model.*;
import com.morgage.model.data.UserInfoData;
import com.morgage.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PawnerService {
    private final PawnerRepository pawnerRepository;
    private final PawnerFavoriteItemRepository pawnerFavoriteItemRepository;
    private final PawneeFavoriteShopRepository pawneeFavoriteShopRepository;
    private final SaleItemRepository saleItemRepository;
    private final TransactionRepository transactionRepository;
    private final ShopRepository shopRepository;
    private final NotificationRepository notificationRepository;

    public PawnerService(PawnerRepository pawnerRepository, PawnerFavoriteItemRepository pawnerFavoriteItemRepository, PawneeFavoriteShopRepository pawneeFavoriteShopRepository, SaleItemRepository saleItemRepository, TransactionRepository transactionRepository, ShopRepository shopRepository, NotificationRepository notificationRepository) {
        this.pawnerRepository = pawnerRepository;
        this.pawnerFavoriteItemRepository = pawnerFavoriteItemRepository;
        this.pawneeFavoriteShopRepository = pawneeFavoriteShopRepository;
        this.saleItemRepository = saleItemRepository;
        this.transactionRepository = transactionRepository;
        this.shopRepository = shopRepository;
        this.notificationRepository = notificationRepository;
    }

    public Pawner setPawnerInfo(int accountId, String email, String phoneNumber, String avaURL, String address) {
        Pawner pawner = getPawnerByAccountId(accountId);
        pawner.setEmail(email);
        pawner.setAddress(address);
        pawner.setPhoneNumber(phoneNumber);
        pawner.setAvaURL(avaURL);
        return pawnerRepository.save(pawner);
    }

    public Pawner getPawnerByAccountId(int accountId) {
        return pawnerRepository.findByAccountId(accountId);
    }

    public UserInfoData getUserInfo(int accountId) {
        Pawner pawner = getPawnerByAccountId(accountId);
        UserInfoData rs = new UserInfoData();
        rs.setAccountId(pawner.getAccountId());
        rs.setAddress(pawner.getAddress());
        rs.setAvaURL(pawner.getAvaURL());
        rs.setEmail(pawner.getEmail());
        rs.setId(pawner.getId());
        rs.setName(pawner.getName());
        rs.setPhoneNumber(pawner.getPhoneNumber());
        List<SaleItem> saleItemsList = new ArrayList<>();
        List<Shop> shopList = new ArrayList<>();
        List<PawnerFavoriteItem> pawnerFavoriteItemList = pawnerFavoriteItemRepository.findAllByPawnerId(rs.getId());
        List<PawnerFavouriteShop> pawnerFavouriteShopsList = pawneeFavoriteShopRepository.findAllByPawnerId(rs.getId());
        for (PawnerFavoriteItem favoriteItem : pawnerFavoriteItemList) {
            saleItemsList.add(saleItemRepository.findById(favoriteItem.getId()));
        }
        rs.setListFavoriteItem(saleItemsList);
        for (PawnerFavouriteShop favouriteShop : pawnerFavouriteShopsList) {
            shopList.add(shopRepository.findShopById(favouriteShop.getShopId()));
        }
        rs.setListFavoriteShop(shopList);
        rs.setListTransaction(transactionRepository.findAllByPawnerId(rs.getId()));
        rs.setListNotification(notificationRepository.findAllByReceiverId(rs.getId()));
        return rs;
    }

    public Integer getAccountIdFromPawnerId(int pawnerId) {
        Pawner pawner = new Pawner();
        pawner = pawnerRepository.findPawnerById(pawnerId);
        if (pawner != null) {
            return pawner.getAccountId();
        } else return null;
    }
}
