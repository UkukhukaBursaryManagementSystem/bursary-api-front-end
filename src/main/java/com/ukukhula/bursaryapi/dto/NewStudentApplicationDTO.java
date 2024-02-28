package com.ukukhula.bursaryapi.dto;

import java.math.BigDecimal;


import lombok.Data;

@Data
public class NewStudentApplicationDTO {
    String FirstName;
    String LastName;
    String IDNumber;
    String PhoneNumber;
    String Email;
    String CourseOfStudy;
    int GenderID;
    int EthnicityID;
    int DepartmentID;
    int UniversityID;
    String ApplicationMotivation;
    BigDecimal BursaryAmount;
    int HeadOfDepartmentID;
    int FundingYear;

    // Constructor to initialize all fields
    public NewStudentApplicationDTO(String firstName, String lastName, String idNumber,
                                    String phoneNumber, String email, String courseOfStudy,
                                    int genderID, int ethnicityID, int departmentID,
                                    int universityID, String applicationMotivation,
                                    BigDecimal bursaryAmount, int headOfDepartmentID,
                                    int fundingYear) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.IDNumber = idNumber;
        this.PhoneNumber = phoneNumber;
        this.Email = email;
        this.CourseOfStudy = courseOfStudy;
        this.GenderID = genderID;
        this.EthnicityID = ethnicityID;
        this.DepartmentID = departmentID;
        this.UniversityID = universityID;
        this.ApplicationMotivation = applicationMotivation;
        this.BursaryAmount = bursaryAmount;
        this.HeadOfDepartmentID = headOfDepartmentID;
        this.FundingYear = fundingYear;
    }
}
