package com.morgage.service;

import com.morgage.common.Const;
import com.morgage.model.Transaction;
import com.morgage.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void setTransactionStatus(Transaction transaction, int status) {
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id);
    }

    public Transaction createTransaction(Integer pawneeId, int shopId, String itemName, int basePrice,
                                         int paymentTerm, int paymentType, int liquidate, Date startDate, int categoryItemId,
                                         String attribute1, String attribute2, String attribute3, String attribute4) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        if (paymentType == Const.PAYMENT_TYPE.DAY) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + paymentTerm * 1);
        } else if (paymentType == Const.PAYMENT_TYPE.WEEK) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + paymentTerm * 7);
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + paymentTerm * 1);
        }
        Date nestPaymentDate = calendar.getTime();
        Transaction transaction = new Transaction();
        transaction.setAttribute1Value(attribute1);
        transaction.setAttribute3Value(attribute3);
        transaction.setAttribute2Value(attribute2);
        transaction.setAttribute4Value(attribute4);
        transaction.setStatus(Const.TRANSACTION_STATUS.UNACCEPTED);
        transaction.setCategoryItemId(categoryItemId);
        transaction.setItemName(itemName);
        transaction.setLiquidateAfter(liquidate);
        Timestamp timeStamp = new Timestamp(nestPaymentDate.getTime());
        Timestamp start = new Timestamp(startDate.getTime());
        transaction.setNextPaymentDate(timeStamp);
        transaction.setPawnerId(pawneeId);
        transaction.setPaymentTerm(paymentTerm);
        transaction.setPaymentType(paymentType);
        transaction.setPrice(basePrice);
        transaction.setShopId(shopId);
        transaction.setStartDate(start);
        return transactionRepository.saveAndFlush(transaction);
    }
}
