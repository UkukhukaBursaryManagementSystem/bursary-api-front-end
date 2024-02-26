package com.ukukhula.bursaryapi.entities;

import lombok.Data;

@Data
public class HeadOfDepartment {
    private int id;
    private String name;

    public HeadOfDepartment(int id, String name){
        this.id = id;
        this.name = name;
    }
}
