package com.sanjeeban.CoreApartmentService.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "t_user_information",
        schema = "apt_core",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "aadharno"),
                @UniqueConstraint(columnNames = "panno"),
                @UniqueConstraint(columnNames = "mobile"),
                @UniqueConstraint(columnNames = "username")
        }
)
public class TUserInformation extends BaseAuditableEntity{

    @Id
    @Column(name = "user_id", columnDefinition = "BIGINT DEFAULT generate_user_id()")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "dob")
    private String dob;

    @Column(name = "aadharno")
    private String aadharno;

    @Column(name = "panno")
    private String panno;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "flag")
    private String flag;

    @Column(name = "unique_id")
    private String uniqueid;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAadharno() {
        return aadharno;
    }

    public void setAadharno(String aadharno) {
        this.aadharno = aadharno;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }
}