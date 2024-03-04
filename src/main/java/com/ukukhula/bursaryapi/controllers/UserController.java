package com.ukukhula.bursaryapi.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ukukhula.bursaryapi.repositories.UserRepository;
import com.ukukhula.bursaryapi.dto.AdminDTO;
import com.ukukhula.bursaryapi.dto.HodDTO;
import com.ukukhula.bursaryapi.dto.UserLogDTO;
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userRepository.getUserByEmail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }


    @PostMapping("user/insert-admin")
    public ResponseEntity<?> insertAdmin(@RequestBody AdminDTO adminDTO){
        try{
                userRepository.addAdmin(adminDTO);
                return ResponseEntity.ok("{\"message\": \"The new admin "+adminDTO.getFirstName()+ " " +adminDTO.getLastName()+" was added successfully\"}");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("{\\\"error\\\": \\\"Failed to add new,please try again or check if the admin is not already in the system\"}");
        }
    }


    @PostMapping("user/insert-hod")
    public ResponseEntity<?> insertAdmin(@RequestBody HodDTO headOfDepartmentDTO){
        try{

            userRepository.addHod(headOfDepartmentDTO);
            return ResponseEntity.ok("{\"message\": \"The new head of department "+headOfDepartmentDTO.getFirstName()+ " " +headOfDepartmentDTO.getLastName()+" was added successfully\"}");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("{\\\"error\\\": \\\"Failed to add new head of department,please try again or check if the HOD is not already in the system\"}");
        }
    }

    @PostMapping("/user/log")
    public ResponseEntity<?> insertUserLogs(@RequestBody UserLogDTO userLogDTO)
    {
        try
        {
            userRepository.addUserLog(userLogDTO);
            return ResponseEntity.ok("{\"message\": \"User log was added successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"Failed to load log for user\"}");
    }
    }
}
