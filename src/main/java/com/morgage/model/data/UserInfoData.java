package com.morgage.model.data;

import com.morgage.model.*;

import java.util.List;

public class UserInfoData {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private Integer accountId;
    private String avaURL;
    private String address;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private Role role;
    private List<SaleItem> listFavoriteItem;
    private List<Shop> listFavoriteShop;
    private List<Notification> listNotification;
    private List<Transaction> listTransaction;

    public List<Notification> getListNotification() {
        return listNotification;
    }

    public void setListNotification(List<Notification> listNotification) {
        this.listNotification = listNotification;
    }

    public List<Transaction> getListTransaction() {
        return listTransaction;
    }

    public void setListTransaction(List<Transaction> listTransaction) {
        this.listTransaction = listTransaction;
    }

    public UserInfoData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAvaURL() {
        return avaURL;
    }

    public void setAvaURL(String avaURL) {
        this.avaURL = avaURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<SaleItem> getListFavoriteItem() {
        return listFavoriteItem;
    }

    public void setListFavoriteItem(List<SaleItem> listFavoriteItem) {
        this.listFavoriteItem = listFavoriteItem;
    }

    public List<Shop> getListFavoriteShop() {
        return listFavoriteShop;
    }

    public void setListFavoriteShop(List<Shop> listFavoriteShop) {
        this.listFavoriteShop = listFavoriteShop;
    }
}
