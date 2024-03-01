package com.ukukhula.bursaryapi.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ukukhula.bursaryapi.repositories.UserRepository;
import com.ukukhula.bursaryapi.entities.User;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController
{
    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{email}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userRepository.getUserByEmail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
}