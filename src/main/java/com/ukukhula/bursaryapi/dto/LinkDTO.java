package com.ukukhula.bursaryapi.dto;

import lombok.Data;

@Data
public class LinkDTO {
    int ApplicationID;
    String baseURL;

    public LinkDTO(int applicationID, String baseURL){
        this.ApplicationID = applicationID;
        this.baseURL = baseURL;
    }
}
