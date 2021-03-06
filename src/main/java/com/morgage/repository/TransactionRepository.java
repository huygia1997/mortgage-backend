package com.morgage.repository;

import com.morgage.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByShopIdAndStatusNot(int shopId, int status);

    List<Transaction> findAllByNextPaymentDateBetweenAndStatus(Date start, Date end, int status);

    List<Transaction> findAllByNextPaymentDateBeforeAndStatus(Date date, int status);

    List<Transaction> findAllByShopIdAndStatus(int shopId, int status);

    Transaction findById(int id);

    List<Transaction> findAllByPawnerId(int pawneeId);

    Transaction findTransactionById(int id);

}
