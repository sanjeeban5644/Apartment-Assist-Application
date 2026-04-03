package com.sanjeeban.CoreApartmentService.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_resident_profile", schema = "apt_core")
public class TResidentProfile extends BaseAuditableEntity{

    @Id
    @Column(name = "resident_id")
    private Long residentId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "apartment_id")
    private Long aptId;

    @Column(name = "status")
    private String status;


    public Long getResidentId() {
        return residentId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAptId() {
        return aptId;
    }

    public void setAptId(Long aptId) {
        this.aptId = aptId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}