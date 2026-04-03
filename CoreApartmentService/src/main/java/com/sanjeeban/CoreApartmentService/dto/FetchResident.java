package com.sanjeeban.CoreApartmentService.dto;

public class FetchResident {
    private String fetchFlag;
    private String uniqueId;

    private String startRow;

    private String endRow;

    public String getFetchFlag() {
        return fetchFlag;
    }

    public void setFetchFlag(String fetchFlag) {
        this.fetchFlag = fetchFlag;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getStartRow() {
        return startRow;
    }

    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    public String getEndRow() {
        return endRow;
    }

    public void setEndRow(String endRow) {
        this.endRow = endRow;
    }
}
