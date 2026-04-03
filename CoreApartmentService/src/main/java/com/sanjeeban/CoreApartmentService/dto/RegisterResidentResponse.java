package com.sanjeeban.CoreApartmentService.dto;

public class RegisterResidentResponse extends ApiResponse{
    private String residentStatus;

    public String getResidentStatus() {
        return residentStatus;
    }

    public void setResidentStatus(String residentStatus) {
        this.residentStatus = residentStatus;
    }
}
