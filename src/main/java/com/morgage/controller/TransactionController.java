package com.morgage.controller;

import com.morgage.service.HasCategoryItemService;
import com.morgage.service.ShopService;

public class TransactionController {
    private final ShopService shopService;
    private final HasCategoryItemService hasCategoryItemService;

    public TransactionController(ShopService shopService, HasCategoryItemService hasCategoryItemService) {
        this.shopService = shopService;
        this.hasCategoryItemService = hasCategoryItemService;
    }
}
