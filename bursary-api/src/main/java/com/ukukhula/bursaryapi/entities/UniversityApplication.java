package com.ukukhula.bursaryapi.entities;

import lombok.Data;

@Data
public class UniversityApplication {
    private int id;
    private int universityId;
    private String motivation;
    private String statusId;
    private String reviewerComment;

    public UniversityApplication(int id, int universityId, String motivation, String statusId, String reviewerComment) {
        this.id = id;
        this.universityId = universityId;
        this.motivation = motivation;
        this.statusId = statusId;
        this.reviewerComment = reviewerComment;
    }
}
