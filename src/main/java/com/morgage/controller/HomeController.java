package com.morgage.controller;

import com.morgage.model.data.HomeData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller

public class HomeController {
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ResponseEntity<?> getInitData(@RequestParam("lat") float lat, @RequestParam("lng") float lng) {
        try {

//            return new ResponseEntity<List<HomeData>>(hasCategoryItemService.getAllCategoryItem(shopId), HttpStatus.OK);
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
