package com.morgage.service;

import com.morgage.model.Address;
import com.morgage.model.Shop;
import com.morgage.model.data.ShopData;
import com.morgage.repository.AddressRepository;
import com.morgage.repository.ShopRepository;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final AddressRepository addressRepository;
    private final ShopRepository shopRepository;

    public SearchService(AddressRepository addressRepository, ShopRepository shopRepository) {
        this.addressRepository = addressRepository;
        this.shopRepository = shopRepository;
    }

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

    public ShopData addShopToShopData(int addressId, ShopData shopData) {
        Shop shop = shopRepository.findShopByAddressId(addressId);
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
    // add address to shop data
    public ShopData addAddressToShopData(int id, ShopData shopData) {
        Address address = addressRepository.findAddressById(id);
        shopData.setFullAddress(address.getFullAddress());
        shopData.setLatitude(address.getLatitude());
        shopData.setLongtitude(address.getLongtitude());

        return shopData;

    }

    public ShopData addAddressToShopData(Address address, ShopData shopData) {
        shopData.setFullAddress(address.getFullAddress());
        shopData.setLatitude(address.getLatitude());
        shopData.setLongtitude(address.getLongtitude());

        return shopData;

    }


}
