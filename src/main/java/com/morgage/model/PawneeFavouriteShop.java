package com.morgage.model;

import javax.persistence.*;

@Entity
@Table(name = "pawnee_favourite_shop")
public class PawneeFavouriteShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "pawnee_id")
    private int pawnerId;
    @Column(name = "shop_id")
    private int shopId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPawnerId() {
        return pawnerId;
    }

    public void setPawnerId(int pawnerId) {
        this.pawnerId = pawnerId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public PawneeFavouriteShop() {
    }
}
