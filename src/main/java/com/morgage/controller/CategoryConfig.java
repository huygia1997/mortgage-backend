package com.morgage.controller;

import com.morgage.common.Const;
import com.morgage.model.HasCategoryItem;
import com.morgage.model.data.CategoryData;
import com.morgage.service.HasCategoryItemService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Configuration
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@EnableConfigurationProperties
@Controller
public class CategoryConfig {
    private final HasCategoryItemService hasCategoryItemService;

    public CategoryConfig(HasCategoryItemService hasCategoryItemService) {
        this.hasCategoryItemService = hasCategoryItemService;
    }

    @RequestMapping(value = "/tao-cau-hinh-danh-muc", method = RequestMethod.POST)
    public ResponseEntity<?> createCategoryConfig(@RequestParam("shopId") int shopId, @RequestParam("paymentTerm") int paymentTerm, @RequestParam("paymentType") int paymentType, @RequestParam("liquidateAfter") int liquidate, @RequestParam("categoryId") int categoryItemId,
                                                @RequestParam("attributes") List<String> attributes, @RequestParam("categoryItemName") String categoryItemName) {

        try {
            String[] attrs = {"", "", "", ""};
            if (attributes.size() != 0) {
                for (int i = 0; i < attributes.size(); i++) {
                    attrs[i] = attributes.get(i);
                }
            }
            HasCategoryItem hasCategoryItem = hasCategoryItemService.createItem(shopId, paymentTerm, paymentType, liquidate, categoryItemId,
                    attrs[0], attrs[1], attrs[2], attrs[3], categoryItemName, Const.CATEGORY_CONFIG_STATUS.ACTIVE);

            if (hasCategoryItem != null) {
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/danh-muc", method = RequestMethod.GET)
    public ResponseEntity<?> getCategoryItem(@RequestParam("shopId") int shopId) {
        try {
            return new ResponseEntity<List<CategoryData>>(hasCategoryItemService.getAllCategoryItem(shopId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/chinh-sua-cau-hinh-danh-muc", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCategoryConfig(@RequestParam("cateConfigId") int cateConfigId,
                                                  @RequestParam("categoryItemName") String categoryItemName,
                                                  @RequestParam("paymentTerm") int paymentTerm,
                                                  @RequestParam("paymentType") int paymentType,
                                                  @RequestParam("liquidateAfter") int liquidateAfter,
                                                  @RequestParam("attributes") List<String> attributes) {
        try {
            String[] attrs = {"", "", "", ""};
            if (attributes.size() != 0) {
                for (int i = 0; i < attributes.size(); i++) {
                    attrs[i] = attributes.get(i);
                }
            }
            HasCategoryItem hasCategoryItem = hasCategoryItemService.updateItem(cateConfigId, paymentTerm, paymentType, liquidateAfter, attrs[0], attrs[1], attrs[2], attrs[3], categoryItemName);

            return new ResponseEntity<HasCategoryItem>(hasCategoryItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/thay-doi-trang-thai-cau-hinh-danh-muc", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCategoryConfig(@RequestParam("cateConfigId") int cateConfigId,
                                                  @RequestParam("status") int status) {
        try {
            HasCategoryItem hasCategoryItem = hasCategoryItemService.updateStatus(cateConfigId, status);

            return new ResponseEntity<HasCategoryItem>(hasCategoryItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
