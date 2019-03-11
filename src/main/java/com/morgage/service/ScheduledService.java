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
    private final TransactionService transactionService;

    private final Environment env;

    public ScheduledService(TransactionRepository transactionRepository, TransactionService transactionService, NotificationService notificationService, TransactionService transactionService1, Environment env) {
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
        this.transactionService = transactionService1;
        this.env = env;
    }

    HashMap<Integer, Integer> shopCountNotification = new HashMap<Integer, Integer>();

    //    @Scheduled(cron = "0 0 2 * * *")
//    @Scheduled(fixedDelay = 6 * 60 * 1000)
    public void createNotification() throws ParseException {
        Date parsedDate = getDateTimeFormat();
        Date endOfDay = Util.atEndOfDay(parsedDate);
        Date start = Util.atStartOfDay(parsedDate);
        List<Transaction> list = transactionRepository.findAllByNextPaymentDateBetweenAndStatus(start, endOfDay, Const.TRANSACTION_STATUS.UNPAID);
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
            notificationService.createNotification(count + env.getProperty("notification.shop.expire"), Const.NOTIFICATION_TYPE.SYSTEM_SHOP, null, shopId, null);
        }
        shopCountNotification.clear();

    }

    //    @Scheduled(cron = "0 0 1 * * *")
//    @Scheduled(fixedDelay = 6 * 60 * 1000)
    public void createNotificationForLateTransaction() throws ParseException {
        Date parsedDate = getDateTimeFormat();
        Date start = Util.atStartOfDay(parsedDate);
        List<Transaction> list = transactionRepository.findAllByNextPaymentDateBeforeAndStatus(start, Const.TRANSACTION_STATUS.UNPAID);
        for (Transaction transaction : list) {
            transactionService.setTransactionStatus(transaction, Const.TRANSACTION_STATUS.LATE);
            notificationService.createNotification(transaction.getItemName() + env.getProperty("notification.late"), Const.NOTIFICATION_TYPE.SYSTEM_PAWNER, null, transaction.getPawnerId(), transaction.getId());
            if (shopCountNotification.containsKey(transaction.getShopId())) {
                shopCountNotification.put(transaction.getShopId(), shopCountNotification.get(transaction.getShopId()) + 1);
            } else {
                shopCountNotification.put(transaction.getShopId(), 1);
            }
        }

        for (Map.Entry entry : shopCountNotification.entrySet()) {
            String count = entry.getValue().toString();
            int shopId = Integer.parseInt(entry.getKey().toString());
            notificationService.createNotification(count + env.getProperty("notification.shop.late"), Const.NOTIFICATION_TYPE.SYSTEM_SHOP, null, shopId, null);
        }
        shopCountNotification.clear();
    }

    //    @Scheduled(cron = "1 0 0 * * *")
//    @Scheduled(fixedDelay = 6 * 60 * 1000)
    public void createNotificationAndSetStatusForTransaction() throws ParseException {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar today = Calendar.getInstance();
        Calendar transactionDate = Calendar.getInstance();
        Date parsedDate =  dateFormat.parse(timeStamp.toString());
        Date start = Util.atStartOfDay(parsedDate);
        today.setTime(parsedDate);
        List<Transaction> list = transactionRepository.findAllByNextPaymentDateBeforeAndStatus(start, Const.TRANSACTION_STATUS.UNPAID);
        for (Transaction transaction : list) {
            transactionDate.setTime(dateFormat.parse(transaction.getNextPaymentDate().toString()));
            int lateDays = today.get(Calendar.DATE) - transactionDate.get(Calendar.DATE);
            if (lateDays < transaction.getLiquidateAfter()) {
//                transactionService.setTransactionStatus(transaction, Const.TRANSACTION_STATUS.LATE);
                notificationService.createNotification(transaction.getItemName() + env.getProperty("notification.late.begin") + lateDays + env.getProperty("notification.late.end"), Const.NOTIFICATION_TYPE.SYSTEM_PAWNER, null, transaction.getPawnerId(), transaction.getId());
                if (shopCountNotification.containsKey(transaction.getShopId())) {
                    shopCountNotification.put(transaction.getShopId(), shopCountNotification.get(transaction.getShopId()) + 1);
                } else {
                    shopCountNotification.put(transaction.getShopId(), 1);
                }
            } else {
                transactionService.setTransactionStatus(transaction, Const.TRANSACTION_STATUS.WAIT_FOR_LIQUIDATION);
                notificationService.createNotification(transaction.getItemName() + env.getProperty("notification.overDue"), Const.NOTIFICATION_TYPE.SYSTEM_PAWNER, null, transaction.getPawnerId(), transaction.getId());
                notificationService.createNotification("Hợp đồng" + transaction.getId() + env.getProperty("notification.shop.overDue"), Const.NOTIFICATION_TYPE.SYSTEM_SHOP, null, transaction.getShopId(), transaction.getId());
            }
        }
    }

    private Date getDateTimeFormat() throws ParseException {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        Date parsedDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return parsedDate = dateFormat.parse(timeStamp.toString());
    }


}