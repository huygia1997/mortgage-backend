package com.morgage.service;

import com.morgage.model.*;
import com.morgage.model.data.ShopDataForGuest;
import com.morgage.model.data.ShopInformation;
import com.morgage.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final HasCategoryItemRepository hasCategoryItemRepository;
    private final AddressRepository addressRepository;
    private final CategoryRepository categoryRepository;
    private final PawneeRepository pawneeRepository;
    private final PawneeFavoriteShopRepository pawneeFavoriteShopRepository;


    public ShopService(ShopRepository shopRepository, HasCategoryItemRepository hasCategoryItemRepository, AddressRepository addressRepository, CategoryRepository categoryRepository, PawneeRepository pawneeRepository, PawneeRepository pawneeRepository1, PawneeFavoriteShopRepository pawneeFavoriteShopRepository) {
        this.shopRepository = shopRepository;
        this.hasCategoryItemRepository = hasCategoryItemRepository;
        this.addressRepository = addressRepository;
        this.categoryRepository = categoryRepository;
        this.pawneeRepository = pawneeRepository1;
        this.pawneeFavoriteShopRepository = pawneeFavoriteShopRepository;
    }

    public Shop createShop(Shop shopModel, List<Integer> listIdCategory) {
        Shop shop = shopRepository.saveAndFlush(shopModel);
        for (int item :
                listIdCategory) {
            HasCategoryItem record = new HasCategoryItem();
            record.setIdCategoryItem(item);
            record.setIdShop(shop.getId());
            hasCategoryItemRepository.saveAndFlush(record);
        }
        return shop;
    }

    public List<Shop> searchByShopName(String searchValue) {
        List<Shop> listShop = shopRepository.findAllByShopNameContaining(searchValue);

        return listShop;
    }

    public List<Shop> searchByCateId(String cateString) {
        int cateId = Integer.parseInt(cateString);
        List<HasCategoryItem> listHasCategoryItems = hasCategoryItemRepository.findHasCategoryItemsByIdCategoryItem(cateId);
        if (listHasCategoryItems != null) {
            List<Shop> listShop = new ArrayList<>();
            for (int i = 0; i < listHasCategoryItems.size(); i++) {
                Shop shop = shopRepository.findShopById(listHasCategoryItems.get(i).getIdShop());
                if (shop != null) {
                    listShop.add(shop);
                }
            }
            return listShop;
        }


        return null;
    }


    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    public ShopInformation showShopInformation(int shopId, Integer userId) {
        Shop shop = shopRepository.findById(shopId);
        if (shop == null) {
            return null;
        } else {
            ShopInformation shopInformation = new ShopInformation();
            shopInformation.setId(shop.getId());
            shopInformation.setShopName(shop.getShopName());
            shopInformation.setEmail(shop.getEmail());
            shopInformation.setFacebook(shop.getFacebook());
            shopInformation.setPhoneNumber(shop.getPhoneNumber());
            shopInformation.setPolicy(shop.getPolicy());
            shopInformation.setRating(shop.getRating());
            shopInformation.setStatus(shop.getStatus());
            shopInformation.setViewCount(shop.getViewCount());
            Address address = addressRepository.findAddressById(shop.getAddressId());
            if (address != null) {
                shopInformation.setFullAddress(address.getFullAddress());
                shopInformation.setLatitude(address.getLatitude());
                shopInformation.setLongtitude(address.getLongtitude());
            }
            List<String> listCategoryName = new ArrayList<>();
            List<HasCategoryItem> listCategory = hasCategoryItemRepository.findAllByIdShop(shopId);
            if (listCategory != null) {
                for (HasCategoryItem item : listCategory) {
                    listCategoryName.add(categoryRepository.findById(item.getIdCategoryItem()).getCategoryName());
                }
            }
            if (listCategoryName != null) {
                shopInformation.setCategoryItems(listCategoryName);
            }
            shop.setViewCount(shop.getViewCount() + 1);
            shopRepository.save(shop);
            Pawnee pawnee = pawneeRepository.findByAccountId(userId);
            if (pawneeFavoriteShopRepository.findByShopIdAndPawnerId(shopId, pawnee.getId()) != null) {
                shopInformation.setCheckFavorite(true);
            } else {
                shopInformation.setCheckFavorite(false);
            }
            shopInformation.setViewCount(shop.getViewCount());
            shopInformation.setAvaUrl(shop.getAvatarUrl());
            return shopInformation;
        }
    }

    public boolean followShop(int userId, int shopId) {
        Pawnee pawnee = pawneeRepository.findByAccountId(userId);
        if (pawneeFavoriteShopRepository.findByShopIdAndPawnerId(shopId, pawnee.getId()) != null) {
            return false;
        } else {
            PawnerFavouriteShop pawnerFavouriteShop = new PawnerFavouriteShop();
            pawnerFavouriteShop.setPawnerId(pawnee.getId());
            pawnerFavouriteShop.setShopId(shopId);
            pawneeFavoriteShopRepository.saveAndFlush(pawnerFavouriteShop);
            return true;
        }
    }

    public boolean unFollowShop(int userId, int shopId) {
        Pawnee pawnee = pawneeRepository.findByAccountId(userId);
        PawnerFavouriteShop pawnerFavouriteShop = pawneeFavoriteShopRepository.findByShopIdAndPawnerId(shopId, pawnee.getId());
        if (pawnerFavouriteShop == null) {
            return false;
        } else {
            pawneeFavoriteShopRepository.delete(pawnerFavouriteShop);
            return true;
        }
    }

    public ShopDataForGuest showShopInformationForGuest(int shopId) {
        Shop shop = shopRepository.findById(shopId);
        if (shop == null) {
            return null;
        } else {
            Address address = addressRepository.findAddressById(shop.getAddressId());
            List<String> listCategoryName = new ArrayList<>();
            List<HasCategoryItem> listCategory = hasCategoryItemRepository.findAllByIdShop(shopId);
            if (listCategory != null) {
                for (HasCategoryItem item : listCategory) {
                    listCategoryName.add(categoryRepository.findById(item.getIdCategoryItem()).getCategoryName());
                }
            }
            shop.setViewCount(shop.getViewCount() + 1);
            shopRepository.save(shop);
            ShopDataForGuest shopDataForGuest = new ShopDataForGuest(shop.getId(), shop.getShopName(), shop.getPhoneNumber(), shop.getFacebook(), shop.getEmail(), address.getLatitude(), address.getLongtitude(), address.getFullAddress(), shop.getAvatarUrl(), listCategoryName);
            return shopDataForGuest;
        }
    }

    public Integer getAccountIdByShopId(int shopId) {
        Shop shop = new Shop();
        shop = shopRepository.findShopById(shopId);
        if (shop != null) {
            return shop.getAccountId();
        } else return null;
    }

}
