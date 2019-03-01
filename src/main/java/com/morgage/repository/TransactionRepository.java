package com.morgage.repository;

import com.morgage.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByDateEnd(Date dateEnd);

    List<Transaction> findAllByDateEndBetween(Date start, Date end);
}
