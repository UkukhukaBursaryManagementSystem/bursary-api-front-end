package com.ukukhula.bursaryapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties properties;
    public String GenerateToken(String microsoftAccessToken, String username, List<String> roles)
    {
        return JWT.create()
                .withSubject(microsoftAccessToken)
                .withAudience("3de2be63-d983-47bf-bd2f-92e7693477fa")
                .withIssuer("ukukhulaapi.azurewebsites.net")
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("username", username)
                .withClaim("authorities", roles)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));

    }

}
