package com.ukukhula.bursaryapi.dto;

import lombok.Data;

@Data
public class HodDTO {
    String FirstName;
    String LastName;
    String PhoneNumber;
    String Email;
    int DepartmentID;
    int UniversityID;

    public HodDTO(String firstName,
                    String lastName,
                    String phoneNumber,
                    String email,
                    int departmentID,
                    int universityID)
    {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.PhoneNumber = phoneNumber;
        this.Email = email;
        this.DepartmentID = departmentID;
        this.UniversityID = universityID;

    }
}
