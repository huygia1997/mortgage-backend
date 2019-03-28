package com.morgage.model;

import javax.persistence.*;

@Entity
@Table(name = "rate_shop")
public class RateShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "rate")
    private int rate;
    @Column(name = "pawnee_id")
    private int pawneeId;
    @Column(name = "shop_id")
    private int shopId;

    public RateShop() {
    }

    public int getPawneeId() {
        return pawneeId;
    }

    public void setPawneeId(int pawneeId) {
        this.pawneeId = pawneeId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
