package com.morgage.controller;

import com.morgage.model.Address;
import com.morgage.model.Shop;
import com.morgage.model.data.ShopData;
import com.morgage.service.AddressService;
import com.morgage.service.SearchService;
import com.morgage.service.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {
    private final SearchService searchService;
    private final ShopService shopService;
    private final AddressService addressService;


    public SearchController(SearchService searchService, ShopService shopService, AddressService addressService) {
        this.searchService = searchService;
        this.shopService = shopService;
        this.addressService = addressService;
    }
    // search shops by keyword (shopname)
    @RequestMapping("/search/shops")
    @ResponseBody
    public List<ShopData> searchShopKeywordResult(@RequestParam("keyword") String searchValue) {

        try {
            List<ShopData> listData;
            List<Shop> listShop = shopService.searchByShopName(searchValue);
            if (listShop != null) {
                listData = new ArrayList<>();
                for (int i = 0; i < listShop.size(); i++) {
                    ShopData shopData = new ShopData();
                    // fill shop to shop data.
                    shopData = searchService.addShopToShopData(listShop.get(i), shopData);
                    // fill address to shop data.
                    shopData = searchService.addAddressToShopData(listShop.get(i).getAddressId(), shopData);

                    listData.add(shopData);
                }

                return listData;

            }
            return null;
        } catch (Exception e) {
            return null;
        }

    }
    // search items by keyword (itemname)
//    @RequestMapping("/search/shops")

    // search shop nearby (input: lat, lng)
    @RequestMapping("/search/shops/nearby")
    @ResponseBody
    public List<ShopData> searchShopNearbyResult(@RequestParam Map<String,String> requestParams) {
        String lat = requestParams.get("lat");
        String lng = requestParams.get("lng");
        List<ShopData> listData;
        List<Address> listAddress = addressService.searchNearby(lat, lng);
        if (listAddress != null) {
            listData = new ArrayList<>();
            for (int i = 0; i < listAddress.size(); i++) {
                ShopData shopData = new ShopData();
                // fill address to shop data
                shopData = searchService.addAddressToShopData(listAddress.get(i), shopData);
                // fill shop to shop data
                shopData = searchService.addShopToShopData(listAddress.get(i).getId(), shopData);
                listData.add(shopData);
            }


            return listData;
        }


        return null;
    }

    // search shops by keyword (shopname)
    @RequestMapping("/search/shops/filters")
    @ResponseBody
    public List<ShopData> searchShopFilterResult(@RequestParam Map<String,String> requestParams) {
        String districtId = requestParams.get("district");
        String cateId = requestParams.get("cate");

        List<ShopData> listData;
        List<Address> listAddress = addressService.searchByDistrictId(districtId);
        List<Shop> listShop = shopService.searchByCateId(cateId);

        if (listAddress != null && listShop != null) {
            listData = new ArrayList<>();
            for (int i = 0; i < listShop.size(); i++) {
                for (int j = 0; j < listAddress.size(); j++) {
                    if (listShop.get(i).getAddressId() == listAddress.get(j).getId()) {
                        ShopData shopData = new ShopData();
                        shopData = searchService.addShopToShopData(listShop.get(i), shopData);
                        shopData = searchService.addAddressToShopData(listAddress.get(j), shopData);
                        listData.add(shopData);
                    }
                }
            }

            return listData;
        }





        return null;
    }



    //test
    @RequestMapping("/findall")
    @ResponseBody
    public List<Shop> findall() {
        List<Shop> list = shopService.findAll();
        return list;
    }
    //Search shop by category ( category, location, ban kinh)
//    @RequestMapping("/tim/cate")
//    @ResponseBody

}
