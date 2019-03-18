package com.morgage.model.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ShopData implements Serializable {
    private int id;
    private String shopName;
    private String phoneNumber;
    private String facebook;
    private String email;
    private Integer status;
    private Integer rating;
    private Integer policy;
    private String latitude;
    private String longtitude;
    private String fullAddress;
    private String avaURL;

    public String getAvaURL() {
        return avaURL;
    }

    public void setAvaURL(String avaURL) {
        this.avaURL = avaURL;
    }

    public ShopData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }



}
