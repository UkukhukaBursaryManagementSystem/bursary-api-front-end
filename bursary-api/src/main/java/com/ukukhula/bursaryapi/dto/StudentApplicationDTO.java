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
}
