package com.morgage.service;

import com.morgage.model.Address;
import com.morgage.model.HasCategoryItem;
import com.morgage.model.Shop;
import com.morgage.model.data.ShopInformation;
import com.morgage.repository.AddressRepository;
import com.morgage.repository.CategoryItemRepository;
import com.morgage.repository.HasCategoryItemRepository;
import com.morgage.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final HasCategoryItemRepository hasCategoryItemRepository;
    private final AddressRepository addressRepository;
    private final CategoryItemRepository categoryItemRepository;

    public ShopService(ShopRepository shopRepository, HasCategoryItemRepository hasCategoryItemRepository, AddressRepository addressRepository, CategoryItemRepository categoryItemRepository) {
        this.shopRepository = shopRepository;
        this.hasCategoryItemRepository = hasCategoryItemRepository;
        this.addressRepository = addressRepository;
        this.categoryItemRepository = categoryItemRepository;
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

    public ShopInformation showShopInformation(int shopId) {
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
                    listCategoryName.add(categoryItemRepository.findById(item.getIdCategoryItem()).getCategoryName());
                }
            }
            if (listCategoryName != null) {
                shopInformation.setCategoryItems(listCategoryName);
            }
            return shopInformation;
        }
    }


}
