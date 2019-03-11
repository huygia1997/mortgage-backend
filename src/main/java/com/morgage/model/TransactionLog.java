package com.morgage.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "start_payment_date")
    private Date startDate;
    @Column(name = "end_payment_date")
    private Date enđate;
    @Column(name = "status")
    private int status;
    @Column(name = "transaction_id")
    private int transactionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEnđate() {
        return enđate;
    }

    public void setEnđate(Date enđate) {
        this.enđate = enđate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionLog() {
    }
}
