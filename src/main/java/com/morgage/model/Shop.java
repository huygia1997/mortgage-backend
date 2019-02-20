package com.morgage.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "shop")
//@Indexed
public class Shop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idshop")
    private Integer id;
    @Column(name = "shop_name")
    private String shopName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "facebook")
    private String facebook;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    private Integer status;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "policy")
    private Integer policy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getPolicy() {
        return policy;
    }

    public void setPolicy(Integer policy) {
        this.policy = policy;
    }

    public Shop(String shopName, String phoneNumber, String facebook, String email, Integer status, Integer rating, Integer policy) {
        this.shopName = shopName;
        this.phoneNumber = phoneNumber;
        this.facebook = facebook;
        this.email = email;
        this.status = status;
        this.rating = rating;
        this.policy = policy;
    }

    public Shop() {
    }
}
