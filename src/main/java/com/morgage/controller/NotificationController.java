package com.morgage.controller;

import com.morgage.model.data.NotificationData;
import com.morgage.service.NotificationService;
import org.apache.http.client.ClientProtocolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping("/get-notification")
    public ResponseEntity<?> getnotification(HttpServletRequest request) {
        try {
            String AccountId = request.getParameter("id");
            try {
                List<NotificationData> listRs = notificationService.getAllNotification(Integer.parseInt(AccountId));
                return ResponseEntity.ok(listRs);
            } catch (Exception e) {
                return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }
}
