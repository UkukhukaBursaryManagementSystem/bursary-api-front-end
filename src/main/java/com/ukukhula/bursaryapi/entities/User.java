package com.ukukhula.bursaryapi.entities;


import lombok.Data;

@Data
public class User {


    private String firstName;

    private String lastName;

    private String email;

    private String role;

    private Boolean isUserActive;

    public User(String firstName, String lastName, String email, String role, boolean isUserActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.isUserActive = isUserActive;
    }
}
