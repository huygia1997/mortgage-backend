package com.morgage.service;

import com.morgage.common.Const;
import com.morgage.model.Transaction;
import com.morgage.model.TransactionLog;
import com.morgage.repository.TransactionLogRepository;
import com.morgage.repository.TransactionRepository;
import com.morgage.utils.Util;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionLogRepository transactionLogRepository;


    public TransactionService(TransactionRepository transactionRepository, TransactionLogRepository transactionLogRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionLogRepository = transactionLogRepository;
    }

    public void setTransactionStatus(Transaction transaction, int status) {
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id);
    }

    public Transaction createTransaction(int pawneeId, int shopId, String itemName, int basePrice,
                                         int paymentTerm, int paymentType, int liquidate, Date startDate, int categoryItemId,
                                         String attribute1, String attribute2, String attribute3, String attribute4, int pawneeInfoId) {
        Date nestPaymentDate = Util.getEndDay(startDate, paymentType, paymentTerm);
        Transaction transaction = new Transaction();
        transaction.setAttribute1Value(attribute1);
        transaction.setAttribute3Value(attribute3);
        transaction.setAttribute2Value(attribute2);
        transaction.setAttribute4Value(attribute4);
        transaction.setStatus(Const.TRANSACTION_STATUS.UNPAID);
        transaction.setCategoryItemId(categoryItemId);
        transaction.setItemName(itemName);
        transaction.setLiquidateAfter(liquidate);
        Timestamp timeStamp = new Timestamp(nestPaymentDate.getTime());
        Timestamp start = new Timestamp(startDate.getTime());
        transaction.setNextPaymentDate(timeStamp);
        transaction.setPawnerId(pawneeId);
        transaction.setPawneeInfoId(pawneeInfoId);
        transaction.setPaymentTerm(paymentTerm);
        transaction.setPaymentType(paymentType);
        transaction.setPrice(basePrice);
        transaction.setShopId(shopId);
        transaction.setStartDate(start);
        return transactionRepository.saveAndFlush(transaction);
    }

    public TransactionLog createTransactionLog(Date startDate, Date endDate, int status, int transactionId) {
        TransactionLog transactionLog = new TransactionLog();
        Timestamp start = new Timestamp(startDate.getTime());
        Timestamp end = new Timestamp(endDate.getTime());
        transactionLog.setEndDate(end);
        transactionLog.setStartDate(start);
        transactionLog.setStatus(status);
        transactionLog.setTransactionId(transactionId);
        transactionLogRepository.saveAndFlush(transactionLog);
        return transactionLog;
    }

    public List<TransactionLog> getAllTransactionLog(int transactionId) {
        return transactionLogRepository.findAllByTransactionId(transactionId);
    }
    public List<Transaction> getAllTransaction(int shopId) {
        return transactionRepository.findAllByShopId(shopId);
    }

    public TransactionLog paymentTransaction(int transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId);
        if (transaction != null) {
//            transaction.setStartDate(transaction.getNextPaymentDate());
            Date nestPaymentDate = Util.getEndDay(transaction.getNextPaymentDate(), transaction.getPaymentType(), transaction.getPaymentTerm());
            Timestamp timeStamp = new Timestamp(nestPaymentDate.getTime());
            transaction.setNextPaymentDate(timeStamp);
            transactionRepository.save(transaction);
            TransactionLog transactionLog = transactionLogRepository.findByStatusAndTransactionId(Const.TRANSACTION_LOG_STATUS.UNPAID, transactionId);
            if (transactionLog != null) {
                transaction.setStatus(Const.TRANSACTION_LOG_STATUS.PAID);
                transactionLogRepository.save(transactionLog);
                TransactionLog transactionLogNew = new TransactionLog();
                transactionLogNew.setTransactionId(transaction.getId());
                transactionLogNew.setStatus(Const.TRANSACTION_LOG_STATUS.UNPAID);
                transactionLogNew.setStartDate(transactionLog.getEndDate());
                Date endDate = Util.getEndDay(transactionLogNew.getStartDate(), transaction.getPaymentType(), transaction.getPaymentType());
                Timestamp end = new Timestamp(endDate.getTime());
                transactionLogNew.setEndDate(end);
                return  transactionLogRepository.saveAndFlush(transactionLogNew);
            } else return null;
        } else return null;
    }

    public Transaction getTransById(int transId) {
        return transactionRepository.findById(transId);
    }


}
