package com.morgage.model;

import javax.persistence.*;

@Entity
public class HasCategoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "id_shop")
    private int idShop;
    @Column(name = "id_category_item")
    private int idCategoryItem;
    @Column(name = "payment_term")
    private Integer paymentTerm;
    @Column(name = "payment_type")
    private Integer paymentType;
    @Column(name = "liquidate_after")
    private Integer liquidateAfter;



    @Column(name = "attribute_1_name")
    private String attribute1Name;
    @Column(name = "attribute_2_name")
    private String attribute2Name;

    @Column(name = "attribute_3_name")
    private String attribute3Name;

    @Column(name = "attribute_4_name")
    private String attribute4Name;

    @Column(name = "status")
    private Integer status;
    public Integer getLiquidateAfter() {
        return liquidateAfter;
    }

    public void setLiquidateAfter(Integer liquidateAfter) {
        this.liquidateAfter = liquidateAfter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    public int getIdCategoryItem() {
        return idCategoryItem;
    }

    public void setIdCategoryItem(int idCategoryItem) {
        this.idCategoryItem = idCategoryItem;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public HasCategoryItem() {
    }
}
