package com.morgage.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "start_payment_date")
    private Timestamp startDate;
    @Column(name = "next_payment_date")
    private Timestamp nextPaymentDate;
    @Column(name = "base_price")
    private Integer price;
    @Column(name = "status")
    private Integer status;
    @Column(name = "pawner_id")
    private int pawnerId;
    @Column(name = "shop_id")
    private Integer shopId;
    @Column(name = "payment_term")
    private Integer paymentTerm;
    @Column(name = "payment_type")
    private Integer paymentType;
    @Column(name = "liquidate_after")
    private Integer liquidateAfter;
    @Column(name = "attribute_1_value")
    private String attribute1Value;
    @Column(name = "attribute_2_value")
    private String attribute2Value;
    @Column(name = "attribute_3_value")
    private String attribute3Value;
    @Column(name = "attribute_4_value")
    private String attribute4Value;
    @Column(name = "category_item_id")
    private int categoryItemId;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "pawnee_info_id")
    private int pawneeInfoId;

    public int getPawneeInfoId() {
        return pawneeInfoId;
    }

    public void setPawneeInfoId(int pawneeInfoId) {
        this.pawneeInfoId = pawneeInfoId;
    }

    @ManyToOne
    @JoinColumn(name = "pawner_id", insertable = false, updatable = false)
    private Pawner pawner;

    public Pawner getPawner() {
        return pawner;
    }

    @ManyToOne
    @JoinColumn(name = "category_item_id", insertable = false, updatable = false)
    private Category category;

    public Category getCategory() {
        return category;
    }

    @ManyToOne
    @JoinColumn(name = "pawnee_info", insertable = false, updatable = false)
    private PawneeInfo pawneeInfo;

    public PawneeInfo getPawneeInfo() {
        return pawneeInfo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(Timestamp nextPaymentDate) {
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

    public int getPawnerId() {
        return pawnerId;
    }

    public void setPawnerId(int pawnerId) {
        this.pawnerId = pawnerId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(Integer paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getLiquidateAfter() {
        return liquidateAfter;
    }

    public void setLiquidateAfter(Integer liquidateAfter) {
        this.liquidateAfter = liquidateAfter;
    }


    public String getAttribute1Value() {
        return attribute1Value;
    }

    public void setAttribute1Value(String attribute1Value) {
        this.attribute1Value = attribute1Value;
    }

    public String getAttribute2Value() {
        return attribute2Value;
    }

    public void setAttribute2Value(String attribute2Value) {
        this.attribute2Value = attribute2Value;
    }

    public String getAttribute3Value() {
        return attribute3Value;
    }

    public void setAttribute3Value(String attribute3Value) {
        this.attribute3Value = attribute3Value;
    }

    public String getAttribute4Value() {
        return attribute4Value;
    }

    public void setAttribute4Value(String attribute4Value) {
        this.attribute4Value = attribute4Value;
    }

    public int getCategoryItemId() {
        return categoryItemId;
    }

    public void setCategoryItemId(int categoryItemId) {
        this.categoryItemId = categoryItemId;
    }
}
