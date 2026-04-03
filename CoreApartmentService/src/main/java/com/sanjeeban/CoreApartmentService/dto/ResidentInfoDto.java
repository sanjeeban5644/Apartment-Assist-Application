package com.sanjeeban.CoreApartmentService.dto;

import java.util.List;

public class ResidentInfoDto {

    private List<ResidentDetailsDto> residentDetailsList;
    private int totalPages;
    private String responseCode;
    private String responseMessage;

    public List<ResidentDetailsDto> getResidentDetailsList() {
        return residentDetailsList;
    }

    public void setResidentDetailsList(List<ResidentDetailsDto> residentDetailsList) {
        this.residentDetailsList = residentDetailsList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
