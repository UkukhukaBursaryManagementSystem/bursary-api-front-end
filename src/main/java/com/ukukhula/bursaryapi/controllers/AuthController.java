package com.ukukhula.bursaryapi.controllers;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

import com.ukukhula.bursaryapi.dto.AuthRequestDTO;
import com.ukukhula.bursaryapi.dto.JwtResponseDTO;
import com.ukukhula.bursaryapi.security.MsalValidationService;
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
    public void GetToken(@RequestBody @Validated AuthRequestDTO authRequestDTO)
    {

        MsalValidationService msalValidationService = new MsalValidationService();
        msalValidationService.validateSignature(authRequestDTO.getMicrosoftAccessToken());
    }
}
