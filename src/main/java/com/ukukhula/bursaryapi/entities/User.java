package com.ukukhula.bursaryapi.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String role;

    public User(String firstName, String lastName, String email, String role) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        
    }
}
