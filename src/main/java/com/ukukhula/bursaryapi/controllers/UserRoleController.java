package com.ukukhula.bursaryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ukukhula.bursaryapi.repositories.UserRoleRepository;

@RestController
public class UserRoleController {
    @Autowired
    private final UserRoleRepository userRoleRepository;

    public UserRoleController(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;

    }

    @GetMapping("/user-role/{userId}")
    public String getUserRole(@PathVariable int userId) {
        return userRoleRepository.getUserRole(userId);
    }
}