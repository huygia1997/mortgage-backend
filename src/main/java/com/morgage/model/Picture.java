package com.morgage.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "picture")
//@Indexed
public class Picture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "descrition")
    private String descrition;

    public Picture() {
    }

    public Picture(String pictureUrl, String descrition) {
        this.pictureUrl = pictureUrl;
        this.descrition = descrition;
    }

    public Integer getId() {
        return id;
    }


    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }
}
