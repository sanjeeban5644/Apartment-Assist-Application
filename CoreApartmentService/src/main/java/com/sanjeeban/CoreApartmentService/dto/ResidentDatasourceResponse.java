package com.sanjeeban.CoreApartmentService.dto;

public class ResidentDatasourceResponse {
    private UserInformationDtoResponse userInformationDtoResponse;

    public ResidentDatasourceResponse() {
    }

    public UserInformationDtoResponse getUserInformationDtoResponse() {
        return userInformationDtoResponse;
    }

    public void setUserInformationDtoResponse(UserInformationDtoResponse userInformationDtoResponse) {
        this.userInformationDtoResponse = userInformationDtoResponse;
    }
}
