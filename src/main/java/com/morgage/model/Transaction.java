package com.morgage.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "start_payment_date")
    private Date startDate;
    @Column(name = "next_payment_date")
    private Date nextPaymentDate;
    @Column(name = "price")
    private Integer price;
    @Column(name = "status")
    private Integer status;
    @Column(name = "pawner_id")
    private Integer pawnerId;
    @Column(name = "shop_id")
    private Integer shopId;
    @Column(name = "payment_term")
    private int paymentTerm;
    @Column(name = "payment_type")
    private int paymentType;
    @Column(name = "liquidate_after")
    private int liquidateAfter;
    @Column(name = "attribute_1_value")
    private String attribute1Name;
    @Column(name = "attribute_2_value")
    private String attribute2Name;

    @Column(name = "attribute_3_value")
    private String attribute3Name;

    @Column(name = "attribute_4_value")
    private String attribute4Name;
    @Column(name = "category_item_id")
    private int categoryItemId;

    public int getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(int paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public int getLiquidateAfter() {
        return liquidateAfter;
    }

    public void setLiquidateAfter(int liquidateAfter) {
        this.liquidateAfter = liquidateAfter;
    }

    public String getAttribute1Name() {
        return attribute1Name;
    }

    public void setAttribute1Name(String attribute1Name) {
        this.attribute1Name = attribute1Name;
    }

    public String getAttribute2Name() {
        return attribute2Name;
    }

    public void setAttribute2Name(String attribute2Name) {
        this.attribute2Name = attribute2Name;
    }

    public String getAttribute3Name() {
        return attribute3Name;
    }

    public void setAttribute3Name(String attribute3Name) {
        this.attribute3Name = attribute3Name;
    }

    public String getAttribute4Name() {
        return attribute4Name;
    }

    public void setAttribute4Name(String attribute4Name) {
        this.attribute4Name = attribute4Name;
    }

    public int getCategoryItemId() {
        return categoryItemId;
    }

    public void setCategoryItemId(int categoryItemId) {
        this.categoryItemId = categoryItemId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getPawnerId() {
        return pawnerId;
    }

    public void setPawnerId(Integer pawnerId) {
        this.pawnerId = pawnerId;
    }

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

    public Date getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(Date nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Transaction() {
    }
}
