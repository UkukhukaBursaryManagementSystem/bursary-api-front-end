package com.ukukhula.bursaryapi.controllers;

import com.ukukhula.bursaryapi.dto.AuthRequestDTO;
import com.ukukhula.bursaryapi.dto.JwtResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ukukhula.bursaryapi.security.JwtService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class AuthController {

    private final JwtService jwtService;

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @PostMapping("/auth/login")
    public JwtResponseDTO GetToken(@RequestBody @Validated AuthRequestDTO authRequestDTO)
    {
        String userAccessToken = jwtService.GenerateToken(
                authRequestDTO.getMicrosoftAccessToken(),
                authRequestDTO.getUsername(),
                List.of(authRequestDTO.getRole()));
        return JwtResponseDTO
                .builder()
                .accessToken(userAccessToken)
                .build();

    }
}
