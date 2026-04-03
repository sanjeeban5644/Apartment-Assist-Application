package com.sanjeeban.CoreApartmentService.dto;

public class ResidentDetailsDto{
    private String name;
    private String email;
    private String dob;
    private String aadharno;
    private String panno;
    private String mobile;
    private String username;
    private String flag;
    private String uniqueid;
    private String apartmentNumber;

    public ResidentDetailsDto() {
    }

    public ResidentDetailsDto(String name, String email, String dob, String aadharno, String panno, String mobile, String username, String flag, String uniqueid, String apartmentNumber) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.aadharno = aadharno;
        this.panno = panno;
        this.mobile = mobile;
        this.username = username;
        this.flag = flag;
        this.uniqueid = uniqueid;
        this.apartmentNumber = apartmentNumber;
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

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
}
