package com.morgage.model;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "longtitude")
    private String longtitude;
    @Column(name = "full_address")
    private String fullAddress;
    @Column(name = "status")
    private int status;
    @Column(name = "district_id")
    private Integer districtId;

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Address() {
    }
}
