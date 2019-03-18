package com.morgage.controller;

import com.morgage.model.Shop;
import com.morgage.model.Transaction;
import com.morgage.model.data.ShopData;
import com.morgage.service.HasCategoryItemService;
import com.morgage.service.ShopService;
import com.morgage.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TransactionController {
    private final ShopService shopService;
    private final HasCategoryItemService hasCategoryItemService;
    private final TransactionService transactionService;

    public TransactionController(ShopService shopService, HasCategoryItemService hasCategoryItemService, TransactionService transactionService) {
        this.shopService = shopService;
        this.hasCategoryItemService = hasCategoryItemService;
        this.transactionService = transactionService;
    }


    @RequestMapping(value = "/transactions/shopId")
    @ResponseBody
    public List<Transaction> findTransByShopId(@RequestParam("shopId") int shopId) {

        try {
            List<Transaction> listTrans = transactionService.findTransByShopId(shopId);
            if (listTrans != null) {
                return listTrans;
            }


            return null;
        } catch (Exception e) {
            return null;
        }

    }



}
