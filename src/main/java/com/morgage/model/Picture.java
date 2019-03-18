package com.morgage.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "picture")
//@Indexed
public class Picture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "descrition")
    private String descrition;
    @Column(name = "shop_id")
    private Integer shopId;
    @Column(name = "transaction_id")
    private Integer transactionId;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Picture() {
    }

    public Picture(String pictureUrl, String descrition) {
        this.pictureUrl = pictureUrl;
        this.descrition = descrition;
    }

    public Integer getId() {
        return id;
    }


    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }
}
