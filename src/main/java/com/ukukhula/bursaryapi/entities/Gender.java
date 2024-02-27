package com.ukukhula.bursaryapi.entities;

import lombok.Data;

@Data
public class Gender {
    private int id;
    private String name;

    public Gender(int id, String name){
        this.id = id;
        this.name = name;
    }
}
