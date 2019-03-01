package com.morgage.controller;

import com.morgage.model.User;
import com.morgage.repository.UserRepository;
import com.morgage.service.UserService;
import org.apache.http.client.ClientProtocolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/tao-nguoi-dung")
    public ResponseEntity<?> createUser(HttpServletRequest request) {
        try {
            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            String encryptPassword = new BCryptPasswordEncoder().encode(password);
            try {
                User user = userService.initUser(userName, encryptPassword);
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request) throws ClientProtocolException, IOException {
        try {

            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            User user = userService.getUserByUsername(userName);
            if (user == null) {
                return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
            } else {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                    return new ResponseEntity<User>(user, HttpStatus.OK);
                } else return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
}
