package com.ukukhula.bursaryapi.entities;

import lombok.Data;

@Data
public class UniversityApplication {
    private int id;
    private String universityName;
    private String motivation;
    private String applicationStatus;
    private String reviewerComment;

    public UniversityApplication(int id, String universityName, String motivation, String applicationStatus,
            String reviewerComment) {
        this.id = id;
        this.universityName = universityName;
        this.motivation = motivation;
        this.applicationStatus = applicationStatus;
        this.reviewerComment = reviewerComment;
    }
}
