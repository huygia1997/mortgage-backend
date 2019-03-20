package com.morgage.repository;

import com.morgage.model.TransactionItemAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionItemAttributeRepository extends JpaRepository<TransactionItemAttribute, Integer> {
    List<TransactionItemAttribute> findAllByTransactionId(int transId);
}
