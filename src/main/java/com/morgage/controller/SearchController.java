package com.morgage.controller;

import com.morgage.model.Address;
import com.morgage.model.Shop;
import com.morgage.model.data.ShopData;
import com.morgage.service.AddressService;
import com.morgage.service.SearchService;
import com.morgage.service.ShopService;
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
        List<ShopData> listData;
        System.out.println("here");
        System.out.println(searchValue);
        List<Shop> listShop = shopService.searchByShopName(searchValue);
        if (listShop != null) {
            listData = new ArrayList<>();
            for (int i = 0; i < listShop.size(); i++) {
                ShopData shopData = new ShopData();
                // fill shop to shop data.
                shopData = searchService.addShopToShopData(listShop.get(i), shopData);
                // fill address to shop data.
                shopData = addressService.addAddressToShopData(listShop.get(i).getAddressId(), shopData);

                listData.add(shopData);
            }

            return listData;
        }


        return null;
    }
    // search items by keyword (itemname)
//    @RequestMapping("/search/shops")

    // search shop nearby (input: lat, lng)
    @RequestMapping("/search/shops/nearby")
    @ResponseBody
    public List<Address> searchShopNearbyResult(@RequestParam Map<String,String> requestParams) {
        String lat = requestParams.get("lat");
        String lng = requestParams.get("lng");
        List<ShopData> listData;
        List<Address> listAddress = addressService.searchNearby(lat, lng);
        if (listAddress != null) {
            System.out.println(listAddress.get(0));


            return listAddress;
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
