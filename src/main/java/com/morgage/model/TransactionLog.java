package com.morgage.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "date_created")
    private Date dateCreated;
    @Column(name = "date_end")
    private Date dateEnd;
    @Column(name = "status")
    private int status;
    @Column(name = "count_late")
    private int countLate;
    @Column(name = "liquidate_after")
    private int liquidateAfter;
    @Column(name = "transaction_id")
    private int transactionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCountLate() {
        return countLate;
    }

    public void setCountLate(int countLate) {
        this.countLate = countLate;
    }

    public int getLiquidateAfter() {
        return liquidateAfter;
    }

    public void setLiquidateAfter(int liquidateAfter) {
        this.liquidateAfter = liquidateAfter;
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
