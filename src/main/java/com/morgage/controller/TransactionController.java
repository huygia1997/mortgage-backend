package com.morgage.controller;

import com.morgage.common.Const;
import com.morgage.model.*;
import com.morgage.model.data.CategoryData;
import com.morgage.model.data.TransactionDetail;
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


import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@EnableConfigurationProperties
@Controller
public class TransactionController {
    private final ShopService shopService;
    private final HasCategoryItemService hasCategoryItemService;
    private final PictureService pictureService;
    private final PawnerService pawnerService;
    private final TransactionService transactionService;
    private final NotificationService notificationService;
    private final Environment env;
    private final PawneeInfoService pawneeInfoService;

    public TransactionController(ShopService shopService, HasCategoryItemService hasCategoryItemService, PictureService pictureService, PawnerService pawnerService, TransactionService transactionService, NotificationService notificationService, Environment env, PawneeInfoService pawneeInfoService) {
        this.shopService = shopService;
        this.hasCategoryItemService = hasCategoryItemService;
        this.pictureService = pictureService;
        this.pawnerService = pawnerService;
        this.transactionService = transactionService;
        this.notificationService = notificationService;
        this.env = env;
        this.pawneeInfoService = pawneeInfoService;
    }

    @RequestMapping(value = "/tao-hop-dong", method = RequestMethod.POST)
    public ResponseEntity<?> createTransaction(@RequestParam("pawneeId") int pawneeId, @RequestParam("shopId") int shopId, @RequestParam("itemName") String itemName, @RequestParam("basePrice") int basePrice, @RequestParam("pawneeInfoId") int pawneeInfoId,
                                               @RequestParam("paymentTerm") int paymentTerm, @RequestParam("paymentType") int paymentType, @RequestParam("liquidateAfter") int liquidate/*, @RequestParam("startDate") Date startDate*/, @RequestParam("categoryId") int categoryItemId,
                                               @RequestParam("attribute1") String attribute1, @RequestParam("attribute2") String attribute2, @RequestParam("attribute3") String attribute3, @RequestParam("attribute4") String attribute4,
                                               @RequestParam("userName") String userName, @RequestParam("email") String userEmail, @RequestParam("phone") String userPhone, @RequestParam("address") String address, @RequestParam("identifyNumber") String identifyNumber
                                               ,@RequestParam("pictures") List<String> pictures) {
        //test
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        //////
        try {
            int pawneeInfoIdX;
            if (pawneeInfoId == Const.DEFAULT_PAWNEE_INFO_ID) {
                PawneeInfo pawneeInfo = pawneeInfoService.createOneTimePawnee(userName, userEmail, userPhone, identifyNumber, address);
                pawneeInfoIdX = pawneeInfo.getId();
            } else {
                pawneeInfoIdX = pawneeInfoId;
            }

            Transaction transaction = transactionService.createTransaction(pawneeId, shopId, itemName, basePrice, paymentTerm, paymentType, liquidate, startDate, categoryItemId, attribute1, attribute2, attribute3, attribute4, pawneeInfoId);
            if (transaction != null) {
                //create trans log
                transactionService.createTransactionLog(transaction.getStartDate(), transaction.getNextPaymentDate(), Const.TRANSACTION_LOG_STATUS.UNPAID, transaction.getId());
                // insert picture to db
                if (pictures != null) {
                    for (int i=0; i < pictures.size(); i++) {
                        pictureService.savePictureOfTransaction(pictures.get(i), transaction.getId());
                    }
                }
                if (pawneeId != Const.DEFAULT_PAWNEE_ID) {
                    notificationService.createNotification(env.getProperty("acceptMessage"), Const.NOTIFICATION_TYPE.REQUEST, shopId, pawneeId, transaction.getId());
                    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
                } else {
                    //Create pawnee account (optional)

                    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
                    // send Email noti one time user create account
                }
            } else {
                return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/tao-hop-dong", method = RequestMethod.GET)
    public ResponseEntity<?> getCategoryItem(@RequestParam("shopId") int shopId) {
        try {
            return new ResponseEntity<List<CategoryData>>(hasCategoryItemService.getAllCategoryItem(shopId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/lich-su-tra", method = RequestMethod.GET)
    public ResponseEntity<?> getTransactionLogs(@RequestParam("transactionId") int transactionId) {
        try {
            List<TransactionLog> rs = transactionService.getAllTransactionLog(transactionId);
            return new ResponseEntity<List<TransactionLog>>(rs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }
//@RequestMapping(value = "/thanh-toan-hop-dong", method = RequestMethod.POST)
//    public ResponseEntity<?> paidTransaction(@RequestParam("trasactionId") int transactionId){
//
//}

    @RequestMapping(value = "/cam-do", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTransactions(@RequestParam("shopId") int shopId) {
        try {
            List<Transaction> rs = transactionService.getAllTransaction(shopId);
            return new ResponseEntity<List<Transaction>>(rs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/phieu-cam-do", method = RequestMethod.GET)
    public ResponseEntity<?> getTransactionInfo(@RequestParam("transId") int transId) {
        try {
            Transaction transaction = transactionService.getTransById(transId);
            TransactionDetail rs = new TransactionDetail();
            if (transaction != null) {
                rs.setTransaction(transaction);
                List<TransactionLog> transactionLogs = transactionService.getAllTransactionLog(transId);
                rs.setTransactionLogs(transactionLogs);
                List<Picture> pictures = pictureService.getAllPicturesByTransId(transId);
                rs.setPictureList(pictures);
            }
            return new ResponseEntity<TransactionDetail>(rs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/checkExistPawnee", method = RequestMethod.GET)
    public ResponseEntity<?> checkExistPawnee(@RequestParam("email") String email) {
        try {
            List<Pawner> rs = pawnerService.getPawnersByEmail(email);
            return new ResponseEntity<List<Pawner>>(rs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }



}
