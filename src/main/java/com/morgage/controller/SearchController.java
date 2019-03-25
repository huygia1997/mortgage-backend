package com.morgage.controller;

import com.morgage.model.*;
import com.morgage.model.data.ShopData;
import com.morgage.service.AddressService;
import com.morgage.service.HasCategoryItemService;
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
    private final HasCategoryItemService hasCategoryItemService;


    public SearchController(SearchService searchService, ShopService shopService, AddressService addressService, HasCategoryItemService hasCategoryItemService) {
        this.searchService = searchService;
        this.shopService = shopService;
        this.addressService = addressService;
        this.hasCategoryItemService = hasCategoryItemService;
    }
    // search shops by keyword (shopname)
    @RequestMapping("/search/shops")
    public ResponseEntity<?> searchShopKeywordResult(@RequestParam("keyword") String searchValue) {

        try {
            List<Shop> listShop = shopService.searchByShopName(searchValue);
            return new ResponseEntity<List<Shop>>(listShop, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }

    }
    // search items by keyword (itemname)
//    @RequestMapping("/search/shops")

    // search shop nearby (input: lat, lng)
    @RequestMapping("/search/shops/nearby")
    public ResponseEntity<?> searchShopNearbyResult(@RequestParam Map<String,String> requestParams) {
        String latString = requestParams.get("lat");
        String lngString = requestParams.get("lng");
        try {
            Float lat = Float.parseFloat(latString);
            Float lng = Float.parseFloat(lngString);
            List<Shop> listShops = shopService.searchNearby(lat, lng);
            return new ResponseEntity<List<Shop>>(listShops, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }

    }

    // search shops by filter (cateId, districtID)
    @RequestMapping("/search/shops/filters")
    public ResponseEntity<?> searchShopFilterResult(@RequestParam("cate") int cateId, @RequestParam("district") int disId) {

        try {
            List<Shop> list = shopService.getShopFilter(cateId, disId);
            return new ResponseEntity<List<Shop>>(list, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }


    //test
    @RequestMapping("/findall")
    @ResponseBody
    public List<Shop> findall() {
        List<Shop> list = shopService.findAll();
        return list;
    }
    @RequestMapping("/getCityDistrictData")
    @ResponseBody
    public Map<String, List<District>> getCityDistrictData() {
        Map<String, List<District>> listData = searchService.searchCityDistrict();
        return listData;
    }

    @RequestMapping("/getCity")
    @ResponseBody
    public List<City> getCity() {
        List<City> list = searchService.findAllCities();
        return list;
    }

    @RequestMapping("/getDistrict")
    @ResponseBody
    public List<District> getDistrict() {
        List<District> list = searchService.findAllDistrict();
        return list;
    }

    @RequestMapping("/getCategory")
    @ResponseBody
    public List<Category> getCategory() {
        List<Category> list = searchService.findAllCategory();
        return list;
    }




}
