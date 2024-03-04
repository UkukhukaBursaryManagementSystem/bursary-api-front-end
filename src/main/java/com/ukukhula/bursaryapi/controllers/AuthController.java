package com.ukukhula.bursaryapi.controllers;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

import com.ukukhula.bursaryapi.dto.AuthRequestDTO;
import com.ukukhula.bursaryapi.dto.JwtResponseDTO;
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
    public JwtResponseDTO GetToken(@RequestBody @Validated AuthRequestDTO authRequestDTO)
    {
        String secretKey = "This is my key";
        String issuer = "ukukhulaapi.azurewebsites.net";

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        String token = JWT.create()
                .withSubject(authRequestDTO.getUsername())
                .withIssuer(issuer)
                .withAudience("3de2be63-d983-47bf-bd2f-92e7693477fa")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 14400000))
                .sign(algorithm);
        return JwtResponseDTO.builder().accessToken(token).build();
    }
}
