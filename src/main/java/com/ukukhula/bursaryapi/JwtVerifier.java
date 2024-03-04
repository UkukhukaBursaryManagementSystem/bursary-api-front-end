package com.ukukhula.bursaryapi;

import jakarta.servlet.http.HttpServletRequest;

public class JwtVerifier {

    private final String token;
    private final HttpServletRequest request;
    private final String userRole;
    public JwtVerifier(String token, HttpServletRequest request, String userRole)
    {
        this.token = token;
        this.request = request;
        this.userRole = userRole;
    }

    public boolean verifyToken()
    {
        return false;
    }
}
