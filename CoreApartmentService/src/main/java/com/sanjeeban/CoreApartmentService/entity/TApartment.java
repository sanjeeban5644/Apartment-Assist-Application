package com.sanjeeban.CoreApartmentService.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_apartment", schema = "apt_core")
public class TApartment extends BaseAuditableEntity{

    @Id
    @Column(name = "apartment_id")
    private Long aptId;
    @Column(name = "block_number")
    private String blockNo;
    @Column(name = "flat_number")
    private String flatNo;
    @Column(name = "availability")
    private String availability;
    @Column(name = "carpet_area")
    private Double carpetArea;
    @Column(name = "apartment_number")
    private String apartmentNo;
    @Column(name = "bhk_type")
    private String bhkType;

    public Long getAptId() {
        return aptId;
    }

    public void setAptId(Long aptId) {
        this.aptId = aptId;
    }

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Double getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(Double carpetArea) {
        this.carpetArea = carpetArea;
    }

    public String getApartmentNo() {
        return apartmentNo;
    }

    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

    public String getBhkType() {
        return bhkType;
    }

    public void setBhkType(String bhkType) {
        this.bhkType = bhkType;
    }
}
