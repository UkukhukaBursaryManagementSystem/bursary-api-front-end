package com.ukukhula.bursaryapi.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder {

    private final JwtProperties properties;
    public DecodedJWT decode(String token)
    {
        return JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
                .withIssuer("ukukhulaapi.azurewebsites.net")
                .withAudience("3de2be63-d983-47bf-bd2f-92e7693477fa")
                .build()
                .verify(token);
    }
}
