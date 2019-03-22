package com.morgage.model.data;

import com.morgage.model.Shop;
import com.morgage.model.User;

import java.io.Serializable;

public class UserShop implements Serializable {
    private User user;
    private Shop shop;

    public UserShop() {
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
