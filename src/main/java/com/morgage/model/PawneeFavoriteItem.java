package com.morgage.model;

import javax.persistence.*;

@Entity
public class PawneeFavoriteItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "description")
    private String description;
    @Column(name = "pawnee_id")
    private Integer pawnerId;
    @Column(name = "item_id")
    private Integer itemId;

    public Integer getPawnerId() {
        return pawnerId;
    }

    public void setPawnerId(Integer pawnerId) {
        this.pawnerId = pawnerId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PawneeFavoriteItem() {
    }
}
