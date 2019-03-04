package com.morgage.service;

import com.morgage.common.Const;
import com.morgage.model.Transaction;
import com.morgage.model.TransactionItem;
import com.morgage.repository.TransactionItemRepository;
import com.morgage.repository.TransactionRepository;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Component
public class ScheduledService {
    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;
    private final TransactionItemRepository transactionItemRepository;

    private final Environment env;

    public ScheduledService(TransactionRepository transactionRepository, TransactionService transactionService, NotificationService notificationService, TransactionItemRepository transactionItemRepository, Environment env) {
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
        this.transactionItemRepository = transactionItemRepository;
        this.env = env;
    }

    HashMap<Integer, Integer> shopCountNotification = new HashMap<Integer, Integer>();

        @Scheduled(cron = "0 0 8 * * *")
//    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void createNotification() throws ParseException {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        Date parsedDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        parsedDate = dateFormat.parse(timeStamp.toString());

        List<Transaction> list = transactionRepository.findAllByDateEnd(parsedDate);
        for (Transaction transaction : list) {
            TransactionItem item = transactionItemRepository.findByTransactionId(transaction.getId());
            notificationService.createNotification("Item " + item.getName() + " payment is due", Const.NOTIFICATION_TYPE.SYSTEM_PAWNER, null, transaction.getPawnerId(), transaction.getId());
            if (shopCountNotification.containsKey(transaction.getShopId())) {
                shopCountNotification.put(transaction.getShopId(), shopCountNotification.get(transaction.getShopId()) + 1);
            } else {
                shopCountNotification.put(transaction.getShopId(), 1);
            }
        }
        for (Map.Entry entry : shopCountNotification.entrySet()) {
            String count = entry.getValue().toString();
            int shopId = Integer.parseInt(entry.getKey().toString());
            notificationService.createNotification("There are " + count + " payments due", Const.NOTIFICATION_TYPE.SYSTEM_SHOP, null, shopId, shopId);
        }
    }
}
