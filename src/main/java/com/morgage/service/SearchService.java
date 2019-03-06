package com.morgage.service;

import com.morgage.model.Shop;
import com.morgage.model.data.ShopData;
import org.springframework.stereotype.Service;

@Service
public class SearchService {


    //Add Shop to Shop Data.
    public ShopData addShopToShopData(Shop shop, ShopData shopData) {
        shopData.setId(shop.getId());
        shopData.setShopName(shop.getShopName());
        shopData.setEmail(shop.getEmail());
        shopData.setFacebook(shop.getFacebook());
        shopData.setPhoneNumber(shop.getPhoneNumber());
        shopData.setPolicy(shop.getPolicy());
        shopData.setRating(shop.getRating());
        shopData.setStatus(shop.getStatus());

        return shopData;
    }


}
