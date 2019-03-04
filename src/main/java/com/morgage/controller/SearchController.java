package com.morgage.controller;

import com.morgage.model.Shop;
import com.morgage.model.ShopData;
import com.morgage.service.AddressService;
import com.morgage.service.SearchService;
import com.morgage.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
    public List<ShopData> searchResult(@RequestParam("keyword") String searchValue) {
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
