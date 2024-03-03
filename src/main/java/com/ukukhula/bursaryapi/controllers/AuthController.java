package com.ukukhula.bursaryapi.controllers;

import com.ukukhula.bursaryapi.dto.AuthRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @PostMapping("/auth/login")

    public String GetToken(@RequestBody @Validated AuthRequestDTO authRequestDTO)
    {
            return "I have logged in";
    }
}
