package com.ukukhula.bursaryapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ukukhula.bursaryapi.entities.Request;
import com.ukukhula.bursaryapi.repositories.RequestRepository;

@RestController
public class RequestsController {

    private RequestRepository requestRepository;

    public RequestsController( RequestRepository requestRepository )
    {
        this.requestRepository = requestRepository;
    }
    @PostMapping("/requestAccess")
    public ResponseEntity<?> sendRequest(@RequestBody Request request)
    {
        try {
            requestRepository.createRequest(request);
            return ResponseEntity.ok("{\"message\": \"Request sent successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\\\"error\\\": \\\"Failed to send request, please ensure all form fields are valid\"}");
        }
    }
}