package com.morgage.controller;

import com.morgage.model.data.ShopDataForGuest;
import com.morgage.model.data.ShopInformation;
import com.morgage.service.PawneeService;
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
    private final PawneeService pawneeService;

    public ShopController(ShopService shopService, PawneeService pawneeService) {
        this.shopService = shopService;
        this.pawneeService = pawneeService;
    }

    @RequestMapping(value = "/thong-tin-shop", method = RequestMethod.GET)
    public ResponseEntity<?> getShopInformation(@RequestParam("shopId") int shopId, @RequestParam("userId") Integer userId) {
            ShopInformation shopInformation = shopService.showShopInformation(shopId, userId);
            if (shopInformation != null) {
                return new ResponseEntity<ShopInformation>(shopInformation, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
            }
    }

    @RequestMapping(value = "/khach/thong-tin-cua-hang")
    public ResponseEntity<?> getShopInformation(@RequestParam("shopId") String shopId) {
        ShopDataForGuest shopInformation = shopService.showShopInformationForGuest(Integer.parseInt(shopId));
        if (shopInformation != null) {
            return new ResponseEntity<ShopDataForGuest>(shopInformation, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/quan-tam-cua-hang", method = RequestMethod.GET)
    public ResponseEntity<?> followShop(@RequestParam("shopId") int shopId, @RequestParam("userId") Integer userId) {
            if (shopService.followShop(userId, shopId)) {
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
            }
    }

    @RequestMapping(value = "/bo-quan-tam-cua-hang", method = RequestMethod.GET)
    public ResponseEntity<?> unFollowShop(@RequestParam("shopId") int shopId, @RequestParam("userId") int userId) {
            if (shopService.unFollowShop(userId, shopId)) {
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
            }
    }
//    @RequestMapping(value = "/tro-thanh-cua-hang", method = RequestMethod.POST)
//    public ResponseEntity<?> registerToShop(@RequestParam("accountId") int accountId, @RequestParam("shopName") String shopName, @RequestParam("facebook") String facebook, @RequestParam("email") String email, @RequestParam("phoneNumber")String phone){
//ShopService
//    }
}
