package com.morgage.service;

import com.morgage.model.HasCategoryItem;
import com.morgage.model.Shop;
import com.morgage.repository.HasCategoryItemRepository;
import com.morgage.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final HasCategoryItemRepository hasCategoryItemRepository;

    public ShopService(ShopRepository shopRepository, HasCategoryItemRepository hasCategoryItemRepository) {
        this.shopRepository = shopRepository;
        this.hasCategoryItemRepository = hasCategoryItemRepository;
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

//    public List<Shop> searchNearby(String lat, String lng) {
//        List<Shop> listShop = shopRepository.findAllByShopNameContaining(searchValue);
//
//        return listShop;
//    }


    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

}
