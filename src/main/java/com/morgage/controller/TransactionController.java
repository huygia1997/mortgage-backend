package com.morgage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morgage.common.Const;
import com.morgage.model.*;
import com.morgage.model.data.CategoryData;
import com.morgage.model.data.ExistPawneeData;
import com.morgage.model.data.TransactionDetail;
import com.morgage.service.*;
import com.morgage.utils.Util;
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


import java.util.*;

@Configuration
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@EnableConfigurationProperties
@Controller
public class TransactionController {
    private final ShopService shopService;
    private final HasCategoryItemService hasCategoryItemService;
    private final PictureService pictureService;
    private final PawneeService pawneeService;
    private final TransactionService transactionService;
    private final NotificationService notificationService;
    private final Environment env;
    private final PawneeInfoService pawneeInfoService;

    public TransactionController(ShopService shopService, HasCategoryItemService hasCategoryItemService, PictureService pictureService, PawneeService pawneeService, TransactionService transactionService, NotificationService notificationService, Environment env, PawneeInfoService pawneeInfoService) {
        this.shopService = shopService;
        this.hasCategoryItemService = hasCategoryItemService;
        this.pictureService = pictureService;
        this.pawneeService = pawneeService;
        this.transactionService = transactionService;
        this.notificationService = notificationService;
        this.env = env;
        this.pawneeInfoService = pawneeInfoService;
    }

    @RequestMapping(value = "/tao-hop-dong", method = RequestMethod.POST)
    public ResponseEntity<?> createTransaction(@RequestParam("pawneeId") int pawneeId, @RequestParam("shopId") int shopId, @RequestParam("itemName") String itemName, @RequestParam("pawneeInfoId") int pawneeInfoId, @RequestParam("note") String description, @RequestParam("basePrice") String basePrice,
                                               @RequestParam("paymentTerm") int paymentTerm, @RequestParam("paymentType") int paymentType, @RequestParam("liquidateAfter") int liquidate, @RequestParam("startDate") Date startDate, @RequestParam("categoryId") int categoryId,
                                               @RequestParam("pawneeName") String pawneeName, @RequestParam("email") String userEmail, @RequestParam("phone") String userPhone, @RequestParam("address") String address, @RequestParam("identityNumber") String identityNumber
            , @RequestParam("pictures") String pictures,
                                               @RequestParam("attributes") List<String> attributes) {
        //test
//        Calendar calendar = Calendar.getInstance();
//        Date startDate = startDateX;
//        System.out.println(startDateX);
        //////
        try {
            int pawneeInfoIdX;
            if (pawneeInfoId == Const.DEFAULT_PAWNEE_INFO_ID) {
                PawneeInfo pawneeInfo = pawneeInfoService.createPawneeInfo(pawneeName, userEmail, userPhone, identityNumber, address);
                pawneeInfoIdX = pawneeInfo.getId();
            } else {
                pawneeInfoIdX = pawneeInfoId;
            }

            Transaction transaction = transactionService.createTransaction(pawneeId, shopId, itemName, description, paymentTerm, paymentType, liquidate, startDate, categoryId, pawneeInfoIdX, basePrice);
            if (transaction != null) {
                //create trans log
                transactionService.createTransactionLog(transaction.getStartDate(), transaction.getNextPaymentDate(), Const.TRANSACTION_LOG_STATUS.UNPAID, transaction.getId());
                //create trans his
                transactionService.createTransactionHistory(transaction.getId(), Const.TRANSACTION_HISTORY.CREATE, env.getProperty("create"));
                // insert attribute to db
                if (attributes.size() != 0) {
                    for (int i = 0; i < attributes.size(); i++) {
                        String attributeName = attributes.get(i).split(":")[0];
                        String attributeValue = attributes.get(i).split(":")[1];
                        transactionService.createTransAttribute(attributeName, attributeValue, transaction.getId());
                    }
                }
                // insert picture to db
                Util util = new Util();
                util.insertPicturesToDB(pictureService, pictures, transaction.getId(), Const.PICTURE_STATUS.TRANSACTION);
                if (pawneeId != Const.DEFAULT_PAWNEE_ID) {
                    notificationService.createNotification(env.getProperty("acceptMessage"), Const.NOTIFICATION_TYPE.TRANSACTION_PAWNEE, pawneeService.getAccountIdFromPawnerId(pawneeId), transaction.getId());
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
            List<Transaction> rs = transactionService.getAllTransaction(shopId, Const.TRANSACTION_STATUS.DEFAULT);
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
                List<Picture> pictures = pictureService.findAllByObjectIdAndStatus(transId, Const.PICTURE_STATUS.TRANSACTION);
                rs.setPictureList(pictures);
                List<TransactionItemAttribute> transactionItemAttributes = transactionService.getAllTransAttr(transId);
                rs.setTransactionItemAttributes(transactionItemAttributes);
                List<TransactionHistory> transactionHistories = transactionService.getTop10TransHis(transId);
                rs.setTransactionHistories(transactionHistories);

            }
            return new ResponseEntity<TransactionDetail>(rs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/checkExistPawnee", method = RequestMethod.GET)
    public ResponseEntity<?> checkExistPawnee(@RequestParam("email") String email, @RequestParam("shopId") int shopId) {
        try {
            ExistPawneeData existPawneeData = new ExistPawneeData();
            // link with pawnee
            List<Pawnee> pawneeList = pawneeService.getPawneeFromEmail(email);
            if (pawneeList.size() != 0) {
                existPawneeData.setPawnee(pawneeList.get(0));
            }
            // return exist pawneeinfo data
            List<PawneeInfo> pawneeInfos = pawneeInfoService.getPawneesByEmail(email, shopId);
            if (pawneeInfos.size() != 0) {
                existPawneeData.setPawneeInfo(pawneeInfos.get(0));
            }
            return new ResponseEntity<ExistPawneeData>(existPawneeData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/tra-lai", method = RequestMethod.POST)
    public ResponseEntity<?> paymentTransaction(@RequestParam("transactionid") int transactionId, @RequestParam("date") Date date) {
        try {
            TransactionLog transactionLog = transactionService.paymentTransaction(transactionId, date);
            if (transactionLog != null) {
                //create trans his
                transactionService.createTransactionHistory(transactionId, Const.TRANSACTION_HISTORY.PAID, env.getProperty("paid"));
                return new ResponseEntity<TransactionLog>(transactionLog, HttpStatus.OK);
            } else return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/dong-hop-dong", method = RequestMethod.POST)
    public ResponseEntity<?> closeTransaction(@RequestParam("transactionid") int transactionId) {
        try {
             if(transactionService.changeTransactionStatus(transactionId,Const.TRANSACTION_STATUS.CANCELED)){
                 transactionService.createTransactionHistory(transactionId, Const.TRANSACTION_HISTORY.CLOSE, env.getProperty("close"));
                 return new ResponseEntity<Boolean>(true, HttpStatus.OK);
             }else {
                return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
             }
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/chuoc-do", method = RequestMethod.POST)
    public ResponseEntity<?> redeemTransaction(@RequestParam("transactionId") int transactionId){
        try {
            if(transactionService.changeTransactionStatus(transactionId,Const.TRANSACTION_STATUS.REDEEMED)){
                transactionService.createTransactionHistory(transactionId, Const.TRANSACTION_HISTORY.REDEEM, env.getProperty("redeem"));
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            }else {
                return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }


}
