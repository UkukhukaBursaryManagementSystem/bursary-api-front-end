package com.ukukhula.bursaryapi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ukukhula.bursaryapi.entities.Request;
import com.ukukhula.bursaryapi.repositories.RequestRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RequestsController {

    private RequestRepository requestRepository;

    public RequestsController(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @PostMapping("/requestAccess")
    public Integer createRequest(@RequestBody Request request) {
        return requestRepository.createRequest(request.getFirstName(), request.getLastName(),
                request.getPhoneNumber(), request.getEmail(), request.getDepartment(),
                request.getUniversityName());
    }

    @GetMapping("/allrequests")
    public ResponseEntity<List<Request>> getAllRequests() {
        List<Request> allRequests = requestRepository.getAllRequests();
        if (allRequests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allRequests, HttpStatus.OK);
    }
}
