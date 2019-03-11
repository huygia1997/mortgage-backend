package com.morgage.service;

import com.morgage.model.Transaction;
import com.morgage.repository.TransactionRepository;
import org.springframework.stereotype.Service;

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
}
