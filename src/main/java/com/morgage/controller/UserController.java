package com.morgage.controller;

import com.morgage.common.Const;
import com.morgage.model.Pawnee;
import com.morgage.model.User;
import com.morgage.service.PawneeService;
import com.morgage.service.UserService;
import com.morgage.utils.UserValidator;
import org.apache.http.client.ClientProtocolException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Configuration
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@EnableConfigurationProperties
@Controller
public class UserController {
    private final UserService userService;
    private final JavaMailSender mailSender;
    private final Environment env;
    private final PawneeService pawneeService;

    public UserController(UserService userService, JavaMailSender mailSender, Environment env, PawneeService pawneeService) {
        this.userService = userService;
        this.mailSender = mailSender;
        this.env = env;
        this.pawneeService = pawneeService;
    }


    @RequestMapping(value = "/dang-ky")
    public ResponseEntity<?> createUser(HttpServletRequest request) {
        try {
            UserValidator validator = new UserValidator();
            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            String encryptPassword = new BCryptPasswordEncoder().encode(password);
            if (!validator.validate(userName)) {
                return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
            } else if (userService.getUserByUsername(userName) != null) {
                return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
            }
            User user = userService.initUser(userName, encryptPassword);
            if (user != null) {
//                send email
                user.setToken(UUID.randomUUID().toString());
                userService.save(user);
                String appUrl = env.getProperty("appUrl");
                SimpleMailMessage registerEmail = new SimpleMailMessage();
                registerEmail.setFrom(env.getProperty("register.emailFrom"));
                registerEmail.setTo(user.getUsername());
                registerEmail.setSubject(env.getProperty("register.emailSubject"));
                registerEmail.setText(env.getProperty("register.emailText") + appUrl
                        + "/register?token=" + user.getToken());
                mailSender.send(registerEmail);
            }
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/dang-nhap")
    public ResponseEntity<?> login(HttpServletRequest request) throws ClientProtocolException, SecurityException, IOException {
        try {

            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            User user = userService.getUserByUsername(userName);
            if (user == null) {
                return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
            } else if (user.getStatus() == Const.USER_STATUS.NOT_ACTIVE) {
                return new ResponseEntity<String>("Unactive account", HttpStatus.UNAUTHORIZED);
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

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ResponseEntity<?> displayResetPasswordPage(@RequestParam("token") String token) {
        if (userService.activeUserAccount(token)) {
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
        }
    }

//    @RequestMapping(value = "/quen-mat-khau")
//    public ResponseEntity<?> forgetPassword(HttpServletRequest request) {
//        request.getParameter()
//        if (userService.activeUserAccount()) {
//            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
//        }
//    }

    @RequestMapping(value = "/thong-tin-nguoi-dung/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInformation(@PathVariable("userId") String id) {
        Pawnee pawnee = pawneeService.getPawneeByAccountId(Integer.parseInt(id));
        if (pawnee != null) {
            return new ResponseEntity<Pawnee>(pawnee, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
        }
    }


}
