package com.morgage.model.data;

import com.morgage.model.*;

import java.io.Serializable;
import java.util.List;

public class TransactionDetail implements Serializable {
    private Transaction transaction;
    private List<Picture> pictureList;
    private List<TransactionLog> transactionLogs;
    private List<TransactionItemAttribute> transactionItemAttributes;
    private List<TransactionHistory> transactionHistories;

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

    public List<TransactionItemAttribute> getTransactionItemAttributes() {
        return transactionItemAttributes;
    }

    public void setTransactionItemAttributes(List<TransactionItemAttribute> transactionItemAttributes) {
        this.transactionItemAttributes = transactionItemAttributes;
    }

    public List<TransactionHistory> getTransactionHistories() {
        return transactionHistories;
    }

    public void setTransactionHistories(List<TransactionHistory> transactionHistories) {
        this.transactionHistories = transactionHistories;
    }
}
