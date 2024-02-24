package com.ukukhula.bursaryapi.controllers;

import com.ukukhula.bursaryapi.entities.LoginResponse;
import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.security.JwtIssuer;

import com.ukukhula.bursaryapi.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthenticationController {

    private final JwtIssuer jwtIssuer;
    private final UserRoleRepository userRoleRepository;

    public AuthenticationController(JwtIssuer jwtIssuer, UserRoleRepository userRoleRepository) {
        this.jwtIssuer = jwtIssuer;
        this.userRoleRepository = userRoleRepository;
    }

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated User userDetails) {
//        System.out.println(userDetails);

        var token = jwtIssuer.issue(1, userDetails.getFirstName(),
                List.of("USER"));
        return LoginResponse.builder().accessToken(token)
                .build();
    }
}
