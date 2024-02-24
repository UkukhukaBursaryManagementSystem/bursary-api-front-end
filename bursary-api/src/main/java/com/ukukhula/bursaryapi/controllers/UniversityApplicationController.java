package com.ukukhula.bursaryapi.controllers;

import com.ukukhula.bursaryapi.entities.University;
import com.ukukhula.bursaryapi.entities.UniversityApplication;
import com.ukukhula.bursaryapi.repositories.UniversityApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UniversityApplicationController {

    final UniversityApplicationRepository universityApplicationRepository;

    public UniversityApplicationController(UniversityApplicationRepository universityApplicationRepository) {
        this.universityApplicationRepository = universityApplicationRepository;
    }

    @GetMapping("universities/application/{universityId}")
    public ResponseEntity<?> getApplicationByUniversityId(@PathVariable int universityId) {
        try {
            UniversityApplication application = universityApplicationRepository
                    .getApplicationByUniversityId(universityId);
            return ResponseEntity.ok(application);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No application found for the university id " + universityId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
