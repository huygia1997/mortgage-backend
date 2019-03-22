package com.morgage.model.data;

import com.morgage.model.Category;
import com.morgage.model.SaleItem;

import java.io.Serializable;
import java.util.List;

public class HomeData implements Serializable {
    private List<Category> categories;
    private List<SaleItem> saleItems;

    public HomeData() {
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }
}
