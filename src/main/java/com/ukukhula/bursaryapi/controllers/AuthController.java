package com.ukukhula.bursaryapi.controllers;

import com.ukukhula.bursaryapi.dto.AuthRequestDTO;
import com.ukukhula.bursaryapi.dto.JwtResponseDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @PostMapping("/auth/login")
    public JwtResponseDTO GetToken(@RequestBody @Validated AuthRequestDTO authRequestDTO)
    {
            return new JwtResponseDTO("I have logged in");
    }
}
