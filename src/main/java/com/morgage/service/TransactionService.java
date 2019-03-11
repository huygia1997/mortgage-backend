package com.morgage.service;

import com.morgage.model.Transaction;
import com.morgage.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findTransByShopId(int shopId) {
        List<Transaction> transactionList = transactionRepository.findAllByShopId(shopId);

        return transactionList;

    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}
