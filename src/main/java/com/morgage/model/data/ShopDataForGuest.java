package com.morgage.model.data;

import com.morgage.model.Category;

import java.util.List;

public class ShopDataForGuest {
    private int id;
    private String shopName;
    private String phoneNumber;
    private String facebook;
    private String email;  private String latitude;
    private String longtitude;
    private String fullAddress;
    private String AvaUrl;
    private List<Category> categoryItems;

    public ShopDataForGuest(int id, String shopName, String phoneNumber, String facebook, String email, String latitude, String longtitude, String fullAddress, String avaUrl, List<Category> categoryItems) {
        this.id = id;
        this.shopName = shopName;
        this.phoneNumber = phoneNumber;
        this.facebook = facebook;
        this.email = email;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.fullAddress = fullAddress;
        AvaUrl = avaUrl;
        this.categoryItems = categoryItems;
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

    public String getAvaUrl() {
        return AvaUrl;
    }

    public void setAvaUrl(String avaUrl) {
        AvaUrl = avaUrl;
    }

    public List<Category> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(List<Category> categoryItems) {
        this.categoryItems = categoryItems;
    }

}
