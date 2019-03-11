package com.morgage.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sales_item")
//@Indexed
public class SaleItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "price")
    private int price;
    @Column(name = "status")
    private Integer status;
    @Column(name = "category_Id")
    private Integer categoryId;
    @Column(name = "view_count")
    private Integer viewCount;
    @Column(name = "transaction_id")
    private Integer transactionId;
    @Column(name = "picture_id")
    private Integer pictureId;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public SaleItem() {
    }

    public SaleItem(int price, int status) {
        this.price = price;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
