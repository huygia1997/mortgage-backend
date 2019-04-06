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

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @RequestMapping(value = "/tao-danh-muc", method = RequestMethod.GET)
    public ResponseEntity<?> getCategory() {
        try {
            return new ResponseEntity<List<Category>>(categoryService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }





    @RequestMapping(value = "/get-all-category")
    public ResponseEntity<?> getAllCategory() {
        try {
            return new ResponseEntity<List<Category>>(categoryService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

}
