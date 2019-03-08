package com.morgage.service;

import com.morgage.common.Const;
import com.morgage.model.Transaction;
import com.morgage.repository.TransactionRepository;
import com.morgage.utils.Util;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@EnableConfigurationProperties
@Component
public class ScheduledService {
    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;

    private final Environment env;

    public ScheduledService(TransactionRepository transactionRepository, TransactionService transactionService, NotificationService notificationService, Environment env) {
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
        this.env = env;
    }

    HashMap<Integer, Integer> shopCountNotification = new HashMap<Integer, Integer>();

        @Scheduled(cron = "0 0 8 * * *")
//    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void createNotification() throws ParseException {
        Date parsedDate = getDatePaterm();
        Date endOfDay = Util.atEndOfDay(parsedDate);
        Date start = Util.atStartOfDay(parsedDate);
//        List<Transaction> list = transactionRepository.findAllByDateEnd(parsedDate);
        List<Transaction> list = transactionRepository.findAllByNextPaymentDateBetween(start, endOfDay);
        for (Transaction transaction : list) {
            notificationService.createNotification(transaction.getItemName() + env.getProperty("notification.expire"), Const.NOTIFICATION_TYPE.SYSTEM_PAWNER, null, transaction.getPawnerId(), transaction.getId());
            if (shopCountNotification.containsKey(transaction.getShopId())) {
                shopCountNotification.put(transaction.getShopId(), shopCountNotification.get(transaction.getShopId()) + 1);
            } else {
                shopCountNotification.put(transaction.getShopId(), 1);
            }
        }
        for (Map.Entry entry : shopCountNotification.entrySet()) {
            String count = entry.getValue().toString();
            int shopId = Integer.parseInt(entry.getKey().toString());
            notificationService.createNotification(count + env.getProperty("notification.shop.expire"), Const.NOTIFICATION_TYPE.SYSTEM_SHOP, null, shopId, shopId);
        }
        shopCountNotification.clear();
    }

    //    @Scheduled(cron = "0 0 9 * * *")
//    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void createNotificationForLateTransaction() throws ParseException {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        Date parsedDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        parsedDate = dateFormat.parse(timeStamp.toString());
    }

    private Date getDatePaterm() throws ParseException {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        Date parsedDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       return parsedDate = dateFormat.parse(timeStamp.toString());
    }
}