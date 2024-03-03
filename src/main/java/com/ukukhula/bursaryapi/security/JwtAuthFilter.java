package com.ukukhula.bursaryapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

import com.ukukhula.bursaryapi.repositories.UserRepository;
import com.ukukhula.bursaryapi.entities.User;

import javax.crypto.SecretKey;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

//    public JwtAuthFilter(SecretKey secretKey) {
//        this.secretKey = secretKey;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer "))
        {
            String jwt = authHeader.substring(7);
            username = jwtService.extractUsername(jwt);

            try
            {
                Claims claims = Jwts.parser().verifyWith(jwtService.getSignKey()).build().parseSignedClaims(jwt).getPayload();
                Authentication authentication = new JwtAuthenticationToken(claims);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                jwtService.validateToken(jwt);
            } catch (Exception e)
            {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        filterChain.doFilter(request, response);
    }
}
