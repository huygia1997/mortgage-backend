package com.morgage.model.data;

import com.morgage.model.Category;
import com.morgage.model.HasCategoryItem;

import java.util.List;

public class ShopInformation {
    private int id;
    private String shopName;
    private String phoneNumber;
    private String facebook;
    private String email;
    private Integer status;
    private Integer rating;
    private String policy;
    private String latitude;
    private String longtitude;
    private String fullAddress;
    private Integer viewCount;
    private Boolean checkFavorite;
    private String AvaUrl;

    public ShopInformation(int id, String shopName, String phoneNumber, String facebook, String email, String latitude, String longtitude, String fullAddress, String avaUrl, List<Category> categoryItems) {
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


    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getAvaUrl() {
        return AvaUrl;
    }

    public void setAvaUrl(String avaUrl) {
        AvaUrl = avaUrl;
    }

    public Boolean getCheckFavorite() {
        return checkFavorite;
    }

    public void setCheckFavorite(Boolean checkFavorite) {
        this.checkFavorite = checkFavorite;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    private List<Category> categoryItems;

    public ShopInformation() {
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

    public void setPolicy(String policy) {
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

    public List<Category> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(List<Category> categoryItems) {
        this.categoryItems = categoryItems;
    }
}
