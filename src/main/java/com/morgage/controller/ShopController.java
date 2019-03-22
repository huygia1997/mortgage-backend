package com.morgage.controller;

import com.morgage.common.Const;
import com.morgage.model.Address;
import com.morgage.model.Shop;
import com.morgage.model.data.ShopDataForGuest;
import com.morgage.model.data.ShopInformation;
import com.morgage.service.AddressService;
import com.morgage.service.PawneeService;
import com.morgage.service.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ShopController {
    private final ShopService shopService;
    private final AddressService addressService;
    private final PawneeService pawneeService;

    public ShopController(ShopService shopService,  AddressService addressService, PawneeService pawneeService) {
        this.shopService = shopService;
        this.addressService = addressService;
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

    @RequestMapping(value = "/de-xuat-cua-hang", method = RequestMethod.GET)
    public ResponseEntity<?> suggestItem(@RequestParam("lat") String latString, @RequestParam("lng") String lngString) {
        try {
            if (latString.equals("none") || lngString.equals("none")) {
                return new ResponseEntity<List<Shop>>(shopService.suggestShopWithoutDistance(), HttpStatus.OK);
            } else {
                Float lat = Float.parseFloat(latString);
                Float lng = Float.parseFloat(lngString);
                return new ResponseEntity<List<Shop>>(shopService.suggestShop(lat, lng), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }

    }


    @RequestMapping(value = "/tro-thanh-cua-hang", method = RequestMethod.POST)
    public ResponseEntity<?> registerToShop(@RequestParam("accountId") int accountId, @RequestParam("shopName") String shopName, @RequestParam("email") String email, @RequestParam("phoneNumber") String phone, @RequestParam("districtId") int districtid,
                                            @RequestParam("address") String fullAddress, @RequestParam("longtitude") String longtitude, @RequestParam("latitude") String latitude) {
        try {
            Address address = addressService.addAddress(longtitude,latitude, fullAddress, districtid);
            if (address != null) {
                Shop shop = new Shop(shopName, phone, email, Const.SHOP_STATUS.UNACTIVE, 0, "", accountId, address.getId(), 0);
                if (shopService.createShop(shop) != null) {
                    return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
                } else return new ResponseEntity<String>("Tài khoản này đã được đăng ký thành chủ tiệm", HttpStatus.NOT_ACCEPTABLE);
            } else {
                return new ResponseEntity<String>("Lỗi dữ liệu", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Lỗi sever, hệ thống đang trục trặc", HttpStatus.BAD_REQUEST);
        }

    }
}
