package com.morgage.repository;

import com.morgage.model.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Integer> {
    List<TransactionLog> findAllByTransactionId(int transactionId);

    TransactionLog findByStatusAndTransactionId(int status, int transactionId);
}
