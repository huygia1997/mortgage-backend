package com.morgage.model.data;

import com.morgage.model.Picture;
import com.morgage.model.Transaction;
import com.morgage.model.TransactionLog;

import java.io.Serializable;
import java.util.List;

public class TransactionDetail implements Serializable {
    private Transaction transaction;
    private List<Picture> pictureList;
    private List<TransactionLog> transactionLogs;

    public TransactionDetail() {
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    public List<TransactionLog> getTransactionLogs() {
        return transactionLogs;
    }

    public void setTransactionLogs(List<TransactionLog> transactionLogs) {
        this.transactionLogs = transactionLogs;
    }
}
