package com.morgage.controller;

import com.morgage.common.Const;
import com.morgage.model.Address;
import com.morgage.model.SaleItem;
import com.morgage.model.Shop;
import com.morgage.model.data.SaleItemDetail;
import com.morgage.model.data.ShopDataForGuest;
import com.morgage.model.data.ShopInformation;
import com.morgage.service.AddressService;
import com.morgage.service.PawneeService;
import com.morgage.service.SaleItemService;
import com.morgage.service.ShopService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopController {
    private final ShopService shopService;
    private final AddressService addressService;
    private final PawneeService pawneeService;
    private final SaleItemService saleItemService;

    public ShopController(ShopService shopService, AddressService addressService, PawneeService pawneeService, SaleItemService saleItemService) {
        this.shopService = shopService;
        this.addressService = addressService;
        this.pawneeService = pawneeService;
        this.saleItemService = saleItemService;
    }

    @RequestMapping(value = "/thong-tin-cua-hang", method = RequestMethod.GET)
    public ResponseEntity<?> getShopInformation(@RequestParam("shopId") int shopId, @RequestParam(value = "userId", required = false) Integer userId) {
        if (userId != null) {
            ShopInformation shopInformation = shopService.showShopInformation(shopId, userId);
            if (shopInformation != null) {
                return new ResponseEntity<ShopInformation>(shopInformation, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
            }
        } else {
            ShopDataForGuest shopInformation = shopService.showShopInformationForGuest(shopId);
            if (shopInformation != null) {
                return new ResponseEntity<ShopDataForGuest>(shopInformation, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
            }
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
    public ResponseEntity<?> suggestItem(@RequestParam(value = "lat", required = false) String latString, @RequestParam(value = "lng", required = false) String lngString, @RequestParam(value = "page", required = false) Integer page) {
        try {
            if (page == null) {
                page = 0;
            }
            Pageable pageable = new PageRequest(page, Const.DEFAULT_ITEM_PER_PAGE);
            if (latString == null || lngString == null) {
                return new ResponseEntity<List<Shop>>(shopService.suggestShopWithoutDistance(pageable), HttpStatus.OK);
            } else {
                Float lat = Float.parseFloat(latString);
                Float lng = Float.parseFloat(lngString);
                return new ResponseEntity<List<Shop>>(shopService.suggestShop(lat, lng, pageable), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/tat-ca-cua-hang", method = RequestMethod.GET)
    public ResponseEntity<?> getAllShop(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "sort", required = false) Integer sortType) {
        try {
            if (page == null) {
                page = 0;
            }
            Sort sort = null;
            if (sortType == Const.SORT_SHOP.ALL) {
                sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
            } else if (sortType == Const.SORT_SHOP.RATING) {
                sort = new Sort(new Sort.Order(Sort.Direction.DESC, "rating"));
            } else {
                sort = new Sort(new Sort.Order(Sort.Direction.DESC, "viewCount"));
            }
            Pageable pageable = new PageRequest(page, Const.DEFAULT_ITEM_PER_PAGE, sort);
            return new ResponseEntity<List<Shop>>(shopService.findAll(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/tro-thanh-cua-hang", method = RequestMethod.POST)
    public ResponseEntity<?> registerToShop(@RequestParam("accountId") int accountId, @RequestParam("shopName") String shopName, @RequestParam("email") String email, @RequestParam("phoneNumber") String phone, @RequestParam("districtId") int districtid,
                                            @RequestParam("address") String fullAddress, @RequestParam("longtitude") String longtitude, @RequestParam("latitude") String latitude) {
        try {
            Address address = addressService.addAddress(longtitude, latitude, fullAddress, districtid);
            if (address != null) {
                Shop shop = new Shop(shopName, phone, email, Const.SHOP_STATUS.UNACTIVE, 0, "", accountId, address.getId(), 0, 0);
                if (shopService.createShop(shop) != null) {
                    return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
                } else
                    return new ResponseEntity<String>("Tài khoản này đã được đăng ký thành chủ tiệm", HttpStatus.NOT_ACCEPTABLE);
            } else {
                return new ResponseEntity<String>("Lỗi dữ liệu", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Lỗi sever, hệ thống đang trục trặc", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/tat-ca-mon-hang", method = RequestMethod.GET)
    public ResponseEntity<?> getAllShopItem(@RequestParam("accountId") int accountId) {
        try {
            Shop shop = shopService.findShopByAccountId(accountId);
            if (shop != null) {
                List<SaleItem> listItem = saleItemService.getItemListByShop(shop.getId());
                List<SaleItemDetail> listRs = new ArrayList<>();
                for (SaleItem item : listItem) {
                    listRs.add(saleItemService.getSaleItemInformation(item.getId(), null, false));
                }
                return new ResponseEntity<List<SaleItemDetail>>(listRs, HttpStatus.OK);
            } else return new ResponseEntity<String>("Lỗi dữ liệu", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>("Lỗi dữ liệu", HttpStatus.BAD_REQUEST);
        }
    }


}
