package com.morgage.controller;

import com.morgage.model.Category;
import com.morgage.model.HasCategoryItem;
import com.morgage.model.data.CategoryData;
import com.morgage.service.CategoryService;
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
public class CategoryController {
    private final HasCategoryItemService hasCategoryItemService;
    private final CategoryService categoryService;

    public CategoryController(HasCategoryItemService hasCategoryItemService, CategoryService categoryService) {
        this.hasCategoryItemService = hasCategoryItemService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/danh-muc", method = RequestMethod.GET)
    public ResponseEntity<?> getCategoryItem(@RequestParam("shopId") int shopId) {
        try {
            return new ResponseEntity<List<CategoryData>>(hasCategoryItemService.getAllCategoryItem(shopId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/tao-danh-muc", method = RequestMethod.GET)
    public ResponseEntity<?> getCategory() {
        try {
            return new ResponseEntity<List<Category>>(categoryService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/tao-danh-muc", method = RequestMethod.POST)
    public ResponseEntity<?> createCategoryItem(@RequestParam("shopId") int shopId, @RequestParam("paymentTerm") int paymentTerm, @RequestParam("paymentType") int paymentType, @RequestParam("liquidateAfter") int liquidate, @RequestParam("categoryId") int categoryItemId,
                                               @RequestParam("attribute1") String attribute1, @RequestParam("attribute2") String attribute2, @RequestParam("attribute3") String attribute3, @RequestParam("attribute4") String attribute4, @RequestParam("categoryItemName") String categoryItemName) {

        try {
            HasCategoryItem hasCategoryItem = hasCategoryItemService.createItem(shopId, paymentTerm, paymentType, liquidate, categoryItemId,
                    attribute1, attribute2, attribute3, attribute4, categoryItemName);

            if (hasCategoryItem != null) {
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }

}