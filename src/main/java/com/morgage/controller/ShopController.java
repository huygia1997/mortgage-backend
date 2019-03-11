package com.morgage.controller;

import com.morgage.model.data.ShopData;
import com.morgage.model.data.ShopInformation;
import com.morgage.service.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShopController {
private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @RequestMapping(value = "/thong-tin-shop", method = RequestMethod.GET)
    public ResponseEntity<?> getShopInformation(@RequestParam("shopId") String shopId) {
        ShopInformation shopInformation= shopService.showShopInformation(Integer.parseInt(shopId));
        if (shopInformation != null) {
            return new ResponseEntity<ShopInformation>(shopInformation, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
        }
    }
}
