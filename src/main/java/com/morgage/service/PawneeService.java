package com.morgage.service;

import com.morgage.model.*;
import com.morgage.model.data.UserInfoData;
import com.morgage.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PawneeService {
    private final PawneeRepository pawneeRepository;
    private final PawneeFavoriteItemRepository pawneeFavoriteItemRepository;
    private final PawneeFavoriteShopRepository pawneeFavoriteShopRepository;
    private final SaleItemRepository saleItemRepository;
    private final ShopRepository shopRepository;
    private final TransactionRepository transactionRepository;
    private final NotificationRepository notificationRepository;

    public PawneeService(PawneeRepository pawneeRepository, PawneeFavoriteItemRepository pawneeFavoriteItemRepository, PawneeFavoriteShopRepository pawneeFavoriteShopRepository, SaleItemRepository saleItemRepository, ShopRepository shopRepository, TransactionRepository transactionRepository, NotificationRepository notificationRepository) {
        this.pawneeRepository = pawneeRepository;
        this.pawneeFavoriteItemRepository = pawneeFavoriteItemRepository;
        this.pawneeFavoriteShopRepository = pawneeFavoriteShopRepository;
        this.saleItemRepository = saleItemRepository;
        this.shopRepository = shopRepository;
        this.transactionRepository = transactionRepository;
        this.notificationRepository = notificationRepository;
    }

    public Pawnee setPawneeInfo(int accountId, String phoneNumber, String avaURL, String address, String name) {
        Pawnee pawnee = getPawneeByAccountId(accountId);
            pawnee.setAddress(address);
        pawnee.setPhoneNumber(phoneNumber);
        pawnee.setAvaURL(avaURL);
        pawnee.setName(name);
        return pawneeRepository.save(pawnee);
    }

    public Pawnee   getPawneeByAccountId(int accountId) {
        return pawneeRepository.findByAccountId(accountId);
    }

    public UserInfoData getUserInfo(int accountId) {
        Pawnee pawnee = getPawneeByAccountId(accountId);
        UserInfoData rs = new UserInfoData();
        rs.setAccountId(pawnee.getAccountId());
        rs.setAddress(pawnee.getAddress());
        rs.setAvaURL(pawnee.getAvaURL());
        rs.setEmail(pawnee.getEmail());
        rs.setId(pawnee.getId());
        rs.setName(pawnee.getName());
        rs.setPhoneNumber(pawnee.getPhoneNumber());
        List<SaleItem> saleItemsList = new ArrayList<>();
        List<Shop> shopList = new ArrayList<>();
        List<PawneeFavoriteItem> pawneeFavoriteItemList = pawneeFavoriteItemRepository.findAllByPawnerId(rs.getId());
        List<PawneeFavouriteShop> pawneeFavouriteShopsList = pawneeFavoriteShopRepository.findAllByPawnerId(rs.getId());
        for (PawneeFavoriteItem favoriteItem : pawneeFavoriteItemList) {
            saleItemsList.add(saleItemRepository.findById(favoriteItem.getId()));
        }
        rs.setListFavoriteItem(saleItemsList);
        for (PawneeFavouriteShop favouriteShop : pawneeFavouriteShopsList) {
            shopList.add(shopRepository.findShopById(favouriteShop.getShopId()));
        }
        rs.setListFavoriteShop(shopList);
        rs.setListTransaction(transactionRepository.findAllByPawnerId(rs.getId()));
        rs.setListNotification(notificationRepository.findAllByReceiverIdOrderByCreateTimeDesc(rs.getId()));
        return rs;
    }

    public Integer getAccountIdFromPawnerId(int pawnerId) {
        Pawnee pawnee = new Pawnee();
        pawnee = pawneeRepository.findPawneeById(pawnerId);
        if (pawnee != null) {
            return pawnee.getAccountId();
        } else return null;
    }

    public List<Pawnee> getPawneeFromEmail(String email) {
        return pawneeRepository.findAllByEmail(email);
    }

}
