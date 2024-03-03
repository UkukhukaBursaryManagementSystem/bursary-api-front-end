package com.ukukhula.bursaryapi.dto;


import lombok.Data;

@Data
public class AdminDTO {
    String FirstName;
    String LastName;
    String PhoneNumber;
    String Email;

    public AdminDTO(String firstName,
                    String lastName,
                    String phoneNumber,
                    String email){
        this.FirstName = firstName;
        this.LastName = lastName;
        this.PhoneNumber = phoneNumber;
        this.Email = email;
    }
}
