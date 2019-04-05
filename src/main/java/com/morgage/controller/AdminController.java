package com.morgage.controller;

import com.morgage.common.Const;
import com.morgage.model.Category;
import com.morgage.model.Shop;
import com.morgage.model.User;
import com.morgage.model.data.UserInfoData;
import com.morgage.repository.RoleRepository;
import com.morgage.service.CategoryService;
import com.morgage.service.PawneeService;
import com.morgage.service.ShopService;
import com.morgage.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    private final ShopService shopService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final PawneeService pawneeService;
    private final RoleRepository roleRepository;

    public AdminController(ShopService shopService, CategoryService categoryService, UserService userService, PawneeService pawneeService, RoleRepository roleRepository) {
        this.shopService = shopService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.pawneeService = pawneeService;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "/chinh-sua-danh-muc")
    public ResponseEntity<?> editCategory(@RequestParam(value = "categoryId") int catId, @RequestParam(value = "name", required = false) String catName, @RequestParam(value = "picUrl") String picUrl) {
        try {
            Category category = categoryService.getCategoryById(catId);
            if (catName != null) {
                category.setCategoryName(catName);
            }
            if (picUrl != null) {
                category.setIconUrl(picUrl);
            }
            return new ResponseEntity<Category>(categoryService.save(category), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("False", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/danh-sach-nguoi-dung")
    public ResponseEntity<?> getAllUser() {
        try {
            List<UserInfoData> listrs = new ArrayList<>();
            List<User> lisetUser = userService.findAll();
            for (User user : lisetUser) {
                listrs.add(pawneeService.getUserInfo(user.getId()));
            }
            return new ResponseEntity<List<UserInfoData>>(listrs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("False", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/sua-thong-tin-nguoi-dung")
    public ResponseEntity<?> editUserRole(@RequestParam("roleId") int role, @RequestParam("email") String email) {
        try {
            User user = userService.getUserByUsername(email);
            if (user != null) {
                return new ResponseEntity<User>(userService.setUserRole(user, role), HttpStatus.OK);
            } else return new ResponseEntity<String>("False", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<String>("False", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/yeu-cau-tro-thanh-cua-hang")
    public ResponseEntity<?> managerShop() {
        try {
            return new ResponseEntity<List<Shop>>(shopService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("False", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/xu-ly-yeu-cau")
    public ResponseEntity<?> processRequest(@RequestParam("shopId") int shopId, @RequestParam("action") boolean action) {
        try {
            Shop shop = shopService.processShopRequest(shopId, action);
            if (shop != null) {
                userService.setUserRolebyUSerId(shopId, Const.ROLE_TYPE.SHOP.getRoleID());
                return new ResponseEntity<Shop>(shop, HttpStatus.OK);
            } else return new ResponseEntity<String>("False", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<String>("False", HttpStatus.BAD_REQUEST);

        }
    }
}
