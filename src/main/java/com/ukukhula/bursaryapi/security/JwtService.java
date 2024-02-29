package com.ukukhula.bursaryapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import com.ukukhula.bursaryapi.entities.User;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
    public String extractUsername(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token)
    {
        return !isTokenExpired(token);
    }

    public String GenerateToken(String username, String role, String microsoftAccessToken)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", username);
        claims.put("role", role);
        claims.put("microsoftAccessToken", microsoftAccessToken);
        return createToken(claims, username);
    }


    public String createToken(Map<String, Object> claims, String username)
    {
        return Jwts.builder()
                .issuer("ukukhulaapi.azurewebsites.net")
                .audience().add("3de2be63-d983-47bf-bd2f-92e7693477fa").and()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*1))
                .signWith(getSignKey()).compact();
    }

    public SecretKey getSignKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
