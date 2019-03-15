package com.morgage.controller;

import com.morgage.common.Const;
import com.morgage.model.Transaction;
import com.morgage.service.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.core.env.Environment;


import java.util.Date;

@Configuration
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@EnableConfigurationProperties
@Controller
public class TransactionController {
    private final ShopService shopService;
    private final HasCategoryItemService hasCategoryItemService;
    private final TransactionService transactionService;
    private final NotificationService notificationService;
    private final Environment env;
    private final OneTimePawneeService oneTimePawneeService;

    public TransactionController(ShopService shopService, HasCategoryItemService hasCategoryItemService, TransactionService transactionService, NotificationService notificationService, Environment env, OneTimePawneeService oneTimePawneeService) {
        this.shopService = shopService;
        this.hasCategoryItemService = hasCategoryItemService;
        this.transactionService = transactionService;
        this.notificationService = notificationService;
        this.env = env;
        this.oneTimePawneeService = oneTimePawneeService;
    }

    @RequestMapping(value = "/tao-hop-dong", method = RequestMethod.POST)
    public ResponseEntity<?> createTransaction(@RequestParam("pawneeId") Integer pawneeId, @RequestParam("shopId") int shopId, @RequestParam("itemName") String itemName, @RequestParam("basePrice") int basePrice,
                                               @RequestParam("paymentTerm") int paymentTerm, @RequestParam("paymentType") int paymentType, @RequestParam("liquidateAfter") int liquidate, @RequestParam("startDate") Date startDate, @RequestParam("categoryId") int categoryItemId,
                                               @RequestParam("attribute1") String attribute1, @RequestParam("attribute2") String attribute2, @RequestParam("attribute3") String attribute3, @RequestParam("attribute4") String attribute4,
                                               @RequestParam("userName") String userName, @RequestParam("email") String userEmail, @RequestParam("phone") String userPhone, @RequestParam("address") String address) {
        try {
            Transaction transaction = transactionService.createTransaction(pawneeId, shopId, itemName, basePrice, paymentTerm, paymentType, liquidate, startDate, categoryItemId, attribute1, attribute2, attribute3, attribute4);
            if (transaction != null) {
                if (pawneeId != Const.DEFAULT_PAWNEE_ID) {
                    notificationService.createNotification(env.getProperty("acceptMessage"), Const.NOTIFICATION_TYPE.REQUEST, shopId, pawneeId, transaction.getId());
                    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
                } else {
                    //Create ont time Pawnee
                    oneTimePawneeService.createOneTimePawnee(userName, userEmail, userPhone, transaction.getId(), address);
                    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
                    // send Email noti one time user create account
                }
            }else {
                return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }
    
}
