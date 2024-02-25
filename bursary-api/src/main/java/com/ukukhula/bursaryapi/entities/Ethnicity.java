package com.ukukhula.bursaryapi.entities;

import lombok.Data;

@Data
public class Ethnicity {
    private int id;
    private String name;

    public Ethnicity(int id, String name){
        this.id = id;
        this.name = name;
    }
}
