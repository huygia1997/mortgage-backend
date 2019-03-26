package com.morgage.model;

import javax.persistence.*;

@Entity
@Table(name = "rate_shop")
public class RateShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

    public Pawnee getPawnee() {
        return pawnee;
    }

    public void setPawnee(Pawnee pawnee) {
        this.pawnee = pawnee;
    }

    @ManyToOne
    @JoinColumn(name = "pawnee_id", referencedColumnName = "id")
    private Pawnee pawnee;
    @Column(name = "rate")
    private int rate;

    public RateShop() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }


    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
