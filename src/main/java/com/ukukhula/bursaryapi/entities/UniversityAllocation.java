package com.ukukhula.bursaryapi.entities;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UniversityAllocation {
    private String universityName;
    private BigDecimal amount;
    private int year;

    public UniversityAllocation( String universityName, BigDecimal amount, int year) { // not necessary
        this.universityName = universityName;
        this.amount = amount;
        this.year = year;
    }

}
