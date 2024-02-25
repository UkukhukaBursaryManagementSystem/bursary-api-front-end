package com.ukukhula.bursaryapi.dto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudentApplicationDTO {
    private Long applicationID;
    private String firstName;
    private String lastName;
    private String IDNumber;
    private String genderIdentity;
    private String ethnicity;
    private String phoneNumber;
    private String email;
    private String universityName;
    private String department;
    private String courseOfStudy;
    private String reviewerComment;
    private String motivation;
    private BigDecimal bursaryAmount;
    private Integer fundingYear;
    private String status;
    private Long headOfDepartmentID;
    private String HODName;

    public StudentApplicationDTO(Long applicationID, String firstName, String lastName, String IDNumber,
                                  String genderIdentity, String ethnicity, String phoneNumber, String email,
                                  String universityName, String department, String courseOfStudy,
                                  String reviewerComment, String motivation, BigDecimal bursaryAmount,
                                  Integer fundingYear, String status, Long headOfDepartmentID, String HODName) {
        this.applicationID = applicationID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.IDNumber = IDNumber;
        this.genderIdentity = genderIdentity;
        this.ethnicity = ethnicity;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.universityName = universityName;
        this.department = department;
        this.courseOfStudy = courseOfStudy;
        this.reviewerComment = reviewerComment;
        this.motivation = motivation;
        this.bursaryAmount = bursaryAmount;
        this.fundingYear = fundingYear;
        this.status = status;
        this.headOfDepartmentID = headOfDepartmentID;
        this.HODName = HODName;
    }

}

