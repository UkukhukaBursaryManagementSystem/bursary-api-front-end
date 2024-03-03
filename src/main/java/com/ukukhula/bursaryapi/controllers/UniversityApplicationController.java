package com.ukukhula.bursaryapi.controllers;

import com.ukukhula.bursaryapi.entities.University;
import com.ukukhula.bursaryapi.entities.UniversityApplication;
import com.ukukhula.bursaryapi.repositories.UniversityApplicationRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UniversityApplicationController {
    @Autowired
    final UniversityApplicationRepository universityApplicationRepository;

    public UniversityApplicationController(UniversityApplicationRepository universityApplicationRepository) {
        this.universityApplicationRepository = universityApplicationRepository;
    }

    @GetMapping("universities/application")
    public ResponseEntity<?> getApplicationByName(@RequestParam String universityName) {
        try {
            UniversityApplication application = universityApplicationRepository
                    .getApplicationByName(universityName);
            return ResponseEntity.ok(application);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No application found for the university with name " + universityName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/universities/applications")
    public ResponseEntity<?> getAllUniversityApplications() {
        try {
            List<UniversityApplication> applications = universityApplicationRepository.getAllUniversityApplications();
            return ResponseEntity.ok(applications);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No applications found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @PostMapping("university-by-admin")
    public Integer addUniversityApplicationByAdmin(@RequestParam String universityName) {
        return universityApplicationRepository.addUniversityApplicationByAdmin(universityName);
    }
}
