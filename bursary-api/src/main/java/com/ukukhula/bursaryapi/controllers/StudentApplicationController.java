package com.ukukhula.bursaryapi.controllers;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ukukhula.bursaryapi.dto.NewStudentApplicationDTO;
import com.ukukhula.bursaryapi.entities.StudentApplication;
import com.ukukhula.bursaryapi.repositories.StudentApplicationRepository;
import com.ukukhula.bursaryapi.exceptions.StudentApplicationException;
import com.ukukhula.bursaryapi.exceptions.ApplicationInvalidStatusException;

@RestController
@RestControllerAdvice
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentApplicationController {

    private final StudentApplicationRepository studentApplicationRepository;

    public StudentApplicationController(StudentApplicationRepository studentApplicationRepository) {
        this.studentApplicationRepository = studentApplicationRepository;

    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentApplications(@PathVariable int studentId) {
        if (studentId <= 0) {
            return ResponseEntity.badRequest().body("Student ID is not provided");
        }
        StudentApplication application = studentApplicationRepository.findByStudentID(studentId);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(application);
    }

    @GetMapping("/students")
    public List<StudentApplication> getAllStudentApplications() {
        List<StudentApplication> applications = studentApplicationRepository.getAllStudentsApplications();
        return applications;
    }

    @ExceptionHandler({ StudentApplicationException.class,
            ApplicationInvalidStatusException.class })

    @PutMapping("/status/{studentID}")
    public ResponseEntity<?> updateStudentsApplicationStatus(@PathVariable int studentID,
            @RequestBody Map<String, String> requestBody) {

        if (studentID <= 0) {
            return ResponseEntity.badRequest().body("Student ID is not provided");
        }

        if (requestBody.isEmpty()) {
            return ResponseEntity.badRequest().body("Request body is empty");
        }

        String statusString = requestBody.get("status");

        if (statusString == null || statusString.isEmpty()) {
            return ResponseEntity.badRequest().body("Status value is missing in the request body");
        }

        try {
            Integer rowsAffected = studentApplicationRepository.updateStudentsApplicationStatus(studentID,
                    statusString);

            if (rowsAffected >= 1) {
                return ResponseEntity.ok("Student status successful");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception error) {
            throw new Error(error.getMessage());
        }
    }

    @PutMapping("/student/updateColumn/{studentID}")
    public ResponseEntity<?> updateStudentsApplicationColumnValue(@PathVariable int studentID,
            @RequestBody Map<String, String> requestBody) {

        if (requestBody.isEmpty()) {
            return ResponseEntity.badRequest().body("Request body is empty");
        }

        String columNameString = new String();

        for (String columName : requestBody.keySet()) {
            columNameString = columName;
        }

        String valueString = requestBody.get(columNameString);

        try {

            if (columNameString == "Status") {
                throw new Error("You have no authority to update Status's");
            }

            Integer rowsAffected = studentApplicationRepository.updateStudentsApplicationColumnValue(studentID,
                    columNameString, valueString);

            if (rowsAffected >= 1) {
                return ResponseEntity.ok("Column update successful");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unsuccessful update");
        }

    }


    @PostMapping("/student-application")
    public ResponseEntity<?> createStudentApplication(@RequestBody NewStudentApplicationDTO application) {
        try {
            int rowsAffected = studentApplicationRepository.insertStudentApplication(application);
            System.out.println("Rows that were affected "+rowsAffected);
            if (rowsAffected == 1) {
                return ResponseEntity.ok("Student application created successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to create student application");
            }
        } catch (SQLException e) {
            
            return ResponseEntity.badRequest().body("Failed to create student application: " + e.getMessage());
        }
    }

}