package com.ukukhula.bursaryapi.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Request {

    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;
    private final int department;
    private final int universityName;

}
