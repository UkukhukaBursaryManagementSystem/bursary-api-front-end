package com.ukukhula.bursaryapi.dto;

import lombok.Data;

@Data
public class HodDTO {
    String FirstName;
    String LastName;
    String PhoneNumber;
    String Email;
    String department;
    String universityName;

    public HodDTO(String firstName,
            String lastName,
            String phoneNumber,
            String email,
            String department,
            String universityName) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.PhoneNumber = phoneNumber;
        this.Email = email;
        this.department = department;
        this.universityName = universityName;

    }
}
