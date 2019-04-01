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
    @Column(name = "id_cloud")
    private String idCloud;
    @Column(name = "descrition")
    private String descrition;
    @Column(name = "delete_hash")
    private String deleteHash;
    @Column(name = "object_id")
    private Integer objectId;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "status")
    private int status;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

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

    public String getIdCloud() {
        return idCloud;
    }

    public void setIdCloud(String idCloud) {
        this.idCloud = idCloud;
    }

    public String getDeleteHash() {
        return deleteHash;
    }

    public void setDeleteHash(String deleteHash) {
        this.deleteHash = deleteHash;
    }
}
