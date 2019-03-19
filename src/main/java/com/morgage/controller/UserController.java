package com.morgage.controller;

import com.morgage.common.Const;
import com.morgage.model.GooglePojo;
import com.morgage.model.GoogleUtils;
import com.morgage.model.Pawner;
import com.morgage.model.User;
import com.morgage.model.data.UserInfoData;
import com.morgage.service.PawnerService;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Configuration
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@EnableConfigurationProperties
@Controller
public class UserController {
    private final UserService userService;
    private final JavaMailSender mailSender;
    private final Environment env;
    private final PawnerService pawnerService;
    private final GoogleUtils googleUtils;

    public UserController(UserService userService, JavaMailSender mailSender, Environment env, PawnerService pawnerService, GoogleUtils googleUtils) {
        this.userService = userService;
        this.mailSender = mailSender;
        this.env = env;
        this.pawnerService = pawnerService;
        this.googleUtils = googleUtils;
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

    @RequestMapping(value = "/quen-mat-khau")
    public ResponseEntity<?> forgetPassword(@RequestParam("email") String email) {
        User user = userService.getUserByUsername(email);
        if (user != null) {

            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/thong-tin-nguoi-dung/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInformation(@PathVariable("userId") String id) {
        UserInfoData pawner = pawnerService.getUserInfo(Integer.parseInt(id));
        if (pawner != null) {
            return new ResponseEntity<UserInfoData>(pawner, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/thay-doi-thong-tin-nguoi-dung", method = RequestMethod.POST)
    public ResponseEntity<?> editUserInformation(@RequestParam("password") String password, @RequestParam("userName") String name, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("acountId") int acountId, @RequestParam("avaUrl") String urlAva, @RequestParam("address") String address) {
        try {
            Pawner pawner = pawnerService.setPawnerInfo(acountId, email, phone, urlAva, address);
            String encryptPassword = new BCryptPasswordEncoder().encode(password);
            User user = userService.editUserInfo(acountId, encryptPassword);
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
        }


    }

    // login with social
    @RequestMapping("/login-google")
    public ResponseEntity<?> loginGoogle(HttpServletRequest request) throws ClientProtocolException, IOException {
        try {
            String code = request.getParameter("code");

            if (code == null || code.isEmpty()) {
                return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            String accessToken = googleUtils.getToken(code);

            GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
            //Save data user
            User user = userService.getUserByUsername(googlePojo.getEmail());
            if (user == null) {
                return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
            } else if (user.getStatus() == Const.USER_STATUS.NOT_ACTIVE) {
                return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }

        } catch (
                Exception e) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
}
