package com.morgage.repository;

import com.morgage.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {
    List<TransactionHistory> findTop10TransactionIdOrderByDateEventDesc(int transId);
}
