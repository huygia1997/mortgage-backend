package com.morgage.model.data;

import com.morgage.model.Shop;
import com.morgage.model.User;

import java.io.Serializable;

public class UserShop implements Serializable {
    private User user;
    private Shop shop;
    private int transDefaultId;

    public UserShop() {
    }

    public int getTransDefaultId() {
        return transDefaultId;
    }

    public void setTransDefaultId(int transDefaultId) {
        this.transDefaultId = transDefaultId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
