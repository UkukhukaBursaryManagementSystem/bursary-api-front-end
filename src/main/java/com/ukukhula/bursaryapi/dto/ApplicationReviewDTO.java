package com.ukukhula.bursaryapi.dto;

import lombok.Data;

@Data
public class ApplicationReviewDTO {
    int ApplicationID;
    int StatusID;
    String ReviewerComment;


    public ApplicationReviewDTO(int applicationID, int statusID,String reviewerComment ){
        this.ApplicationID = applicationID;
        this.StatusID = statusID;
        this.ReviewerComment = reviewerComment;
    }
}
