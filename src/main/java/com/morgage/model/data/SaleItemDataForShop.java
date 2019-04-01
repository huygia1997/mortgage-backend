package com.morgage.model.data;

import com.morgage.model.Picture;
import com.morgage.model.SaleItem;

import java.io.Serializable;
import java.util.List;

public class SaleItemDataForShop implements Serializable {
    private SaleItem saleItem;
    private List<Picture> pictureList;

    public SaleItemDataForShop() {
    }

    public SaleItem getSaleItem() {
        return saleItem;
    }

    public void setSaleItem(SaleItem saleItem) {
        this.saleItem = saleItem;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }
}
