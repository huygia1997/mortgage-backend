package com.morgage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonGenerator;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morgage.common.Const;
import com.morgage.model.PawneeFavoriteItem;
import com.morgage.model.Picture;
import com.morgage.model.SaleItem;
import com.morgage.model.Transaction;
import com.morgage.model.data.SaleItemDataForShop;
import com.morgage.model.data.SaleItemDetail;
import com.morgage.service.*;
import com.morgage.utils.Util;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.*;

@Configuration
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@EnableConfigurationProperties
@Controller
public class SaleItemController {
    private final SaleItemService saleItemService;
    private final TransactionService transactionService;
    private final NotificationService notificationService;
    private final ShopService shopService;
    private final PictureService pictureService;
    private final PawneeService pawneeService;
    private final Environment env;

    public SaleItemController(SaleItemService saleItemService, TransactionService transactionService, NotificationService notificationService, ShopService shopService, PictureService pictureService, PawneeService pawneeService, Environment env) {
        this.saleItemService = saleItemService;
        this.transactionService = transactionService;
        this.notificationService = notificationService;
        this.shopService = shopService;
        this.pictureService = pictureService;
        this.pawneeService = pawneeService;
        this.env = env;
    }


    @RequestMapping(value = "/thong-tin-san-pham", method = RequestMethod.GET)
    public ResponseEntity<?> getItemInformation(@RequestParam("itemId") Integer itemId, @RequestParam(value = "userId", required = false) Integer userId) {
        SaleItemDetail item = saleItemService.getSaleItemInformation(itemId, userId, true);
        if (item != null) {
            return new ResponseEntity<SaleItemDetail>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Can't find item", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/thanh-ly-san-pham", method = RequestMethod.POST)
    public ResponseEntity<?> liquidationItem(@RequestParam("transactionId") Integer transactionId, @RequestParam("picUrl") String picUrl, @RequestParam("price") Integer price, @RequestParam("status") int status) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        if (transaction != null) {
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            SaleItem item = saleItemService.publicItemForSale(transaction, picUrl, price, Const.ITEM_STATUS.WAIT_FOR_LIQUIDATION, timeStamp);
            if (item != null) {
                transactionService.setTransactionStatus(transaction, Const.TRANSACTION_STATUS.LIQUIDATION);
                if (transaction.getPawnerId() != Const.DEFAULT_PAWNEE_ID) {
                    notificationService.createNotification(env.getProperty("notification.user"), Const.NOTIFICATION_TYPE.TRANSACTION_PAWNEE, pawneeService.getAccountIdFromPawnerId(transaction.getPawnerId()), transaction.getId());
                }
                return new ResponseEntity<SaleItem>(item, HttpStatus.OK);
            } else return new ResponseEntity<String>("Can't find item", HttpStatus.BAD_REQUEST);

        } else {
            return new ResponseEntity<String>("Can't find transaction", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/theo-doi-san-pham", method = RequestMethod.GET)
    public ResponseEntity<?> followItem(@RequestParam("itemId") Integer itemId, @RequestParam("userId") Integer userId) {
        if (saleItemService.followItem(itemId, userId)) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/bo-theo-doi-san-pham", method = RequestMethod.GET)
    public ResponseEntity<?> unFollowItem(@RequestParam("itemId") Integer itemId, @RequestParam("userId") Integer userId) {
        if (saleItemService.unFollowItem(itemId, userId)) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/thay-doi-trang-thai-san-pham", method = RequestMethod.POST)
    public ResponseEntity<?> changeItemStatus(@RequestParam("itemId") Integer itemId, @RequestParam("status") Integer status) {
        SaleItem saleItem = saleItemService.changeStatusItem(itemId, status);
        if (saleItem != null) {
            //noti user who follow item
            List<PawneeFavoriteItem> listFavorite = saleItemService.findAllFavoeiteByItemId(itemId);
            if (status == Const.TRANSACTION_STATUS.LIQUIDATION) {
                for (PawneeFavoriteItem record : listFavorite) {
                    notificationService.createNotification("Món hàng " + saleItem.getItemName() + " " + env.getProperty("notification.user.liquidation"), Const.NOTIFICATION_TYPE.ITEM_PAWNEE, pawneeService.getAccountIdFromPawnerId(record.getPawnerId()), saleItem.getId());
                }
            } else {
                for (PawneeFavoriteItem record : listFavorite) {
                    notificationService.createNotification("Món hàng " + saleItem.getItemName() + " " + env.getProperty("notification.user.redeem"), Const.NOTIFICATION_TYPE.ITEM_PAWNEE, pawneeService.getAccountIdFromPawnerId(record.getPawnerId()), saleItem.getId());
                }
            }
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/hang-thanh-ly", method = RequestMethod.GET)
    public ResponseEntity<?> getAllSaleItem(@RequestParam(value = "sort", required = false) Integer sortType, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "categoryid", required = false) Integer categoryId) {
        if (page == null) {
            page = 0;
        }
        Sort sort = null;
        if (sortType == null) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        } else if (sortType == Const.SORT_ITEM.DATE_CREATED) {
            sort = new Sort(new Sort.Order(Sort.Direction.DESC, "liquidationDate"));
        } else if (sortType == Const.SORT_ITEM.LIKE) {
            sort = new Sort(new Sort.Order(Sort.Direction.DESC, "favoriteCount"));
        } else if (sortType == Const.SORT_ITEM.PRICE_ASC) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "price"));
        } else if (sortType == Const.SORT_ITEM.PRICE_DESC) {
            sort = new Sort(new Sort.Order(Sort.Direction.DESC, "price"));
        } else {
            sort = new Sort(new Sort.Order(Sort.Direction.DESC, "viewCount"));
        }
        Pageable pageable = new PageRequest(page, Const.DEFAULT_ITEM_PER_PAGE, sort);
        if (categoryId != null) {
            return new ResponseEntity<List<SaleItem>>(saleItemService.getItemListByCategoryId(categoryId, pageable), HttpStatus.OK);
        } else
            return new ResponseEntity<List<SaleItem>>(saleItemService.getItemList(pageable), HttpStatus.OK);

    }

    @RequestMapping(value = "/san-pham", method = RequestMethod.GET)
    public ResponseEntity<?> getItemByShop(@RequestParam("shopId") int shopId) {
        try {
            List<SaleItem> saleItems = saleItemService.getItemListByShop(shopId);
            List<SaleItemDataForShop> saleItemDataForShops = new ArrayList<>();
            if (saleItems.size() != 0) {
                for (int i=0; i<saleItems.size(); i++) {
                    SaleItemDataForShop saleItemDataForShop = new SaleItemDataForShop();
                    saleItemDataForShop.setSaleItem(saleItems.get(i));
                    saleItemDataForShop.setPictureList(pictureService.findAllByObjectIdAndStatus(saleItems.get(i).getId(), Const.PICTURE_STATUS.ITEM));
                    saleItemDataForShops.add(saleItemDataForShop);
                }
            }
            return new ResponseEntity<List<SaleItemDataForShop>>(saleItemDataForShops, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/tao-san-pham", method = RequestMethod.POST)
    public ResponseEntity<?> createItem(@RequestParam("itemName") String itemName, @RequestParam("price") int price,
                                        @RequestParam("picUrl") String picUrl, @RequestParam("categoryId") int categoryId, @RequestParam("liquidDate") Date liquidDate,
                                        @RequestParam("pictures") String pictures, @RequestParam("transId") int transId, @RequestParam("description") String description) {
        try {
            SaleItem saleItem = saleItemService.createItem(itemName, Const.ITEM_STATUS.WAIT_FOR_LIQUIDATION, price, liquidDate, picUrl, categoryId, transId, 0, 0, description);
            if (saleItem != null) {
                //insert pictures to db:
                Util util = new Util();
                util.insertPicturesToDB(pictureService, pictures, saleItem.getId(), Const.PICTURE_STATUS.ITEM);
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }



    @RequestMapping(value = "/de-xuat-san-pham", method = RequestMethod.GET)
    public ResponseEntity<?> suggestItem(@RequestParam(value = "lat", required = false) String latString, @RequestParam(value = "lng", required = false) String lngString, @RequestParam(value = "page", required = false) Integer page) {
        try {
            if (page == null) {
                page = 0;
            }
            Pageable pageable = new PageRequest(page, Const.DEFAULT_ITEM_PER_PAGE);
            if (latString == null || lngString == null) {
                return new ResponseEntity<List<SaleItem>>(saleItemService.suggestItemWithoutDistance(pageable), HttpStatus.OK);
            } else {
                Float lat = Float.parseFloat(latString);
                Float lng = Float.parseFloat(lngString);
                return new ResponseEntity<List<SaleItem>>(saleItemService.suggestItem(lat, lng, pageable), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }

    }
}
