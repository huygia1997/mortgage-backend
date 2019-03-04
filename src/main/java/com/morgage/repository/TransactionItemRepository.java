package com.morgage.repository;

import com.morgage.model.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, Integer> {
    TransactionItem findByTransactionId(int transactionId);
}
