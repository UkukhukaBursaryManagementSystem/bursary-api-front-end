package com.ukukhula.bursaryapi.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ukukhula.bursaryapi.dto.ApplicationReviewDTO;
import com.ukukhula.bursaryapi.dto.DocumentsDTO;
import com.ukukhula.bursaryapi.dto.NewStudentApplicationDTO;
import com.ukukhula.bursaryapi.dto.StudentApplicationDTO;
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

    @DeleteMapping("/student/{studentId}")
    public String deleteStudentApplication(@PathVariable int studentId) {
        Integer deletedRows = studentApplicationRepository.deleteStudentApplication(studentId);
        return deletedRows > 0 ? "Student application deleted successfully"
                : "Error deleting student";
    }

    @GetMapping("/student-application")
    public ResponseEntity<List<StudentApplicationDTO>> getAllStudentApplicationsDTO() {
        try {
            List<StudentApplicationDTO> applications = studentApplicationRepository.getAllStudentApplicationsDTO();
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving student applications", e);
        }
    }

    @GetMapping("/student-application/{applicationId}")
    public StudentApplicationDTO getStudentApplicationById(@PathVariable Long applicationId) {
        return studentApplicationRepository.getStudentApplicationById(applicationId);
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

    @PutMapping("student-application")
    public Integer updateStudentDetails(@RequestBody Map<String, Object> applicationDetails) {
        int applicationId = (int) applicationDetails.get("applicationId");
        String firstName = (String) applicationDetails.get("firstName");
        String lastName = (String) applicationDetails.get("lastName");
        String idNumber = (String) applicationDetails.get("idNumber");
        String gender = (String) applicationDetails.get("gender");
        String phoneNumber = (String) applicationDetails.get("phoneNumber");
        String email = (String) applicationDetails.get("email");
        String ethnicity = (String) applicationDetails.get("ethnicity");
        String courseOfStudy = (String) applicationDetails.get("courseOfStudy");
        String departmentName = (String) applicationDetails.get("departmentName");
        String reviewerComment = (String) applicationDetails.get("reviewerComment");
        String motivation = (String) applicationDetails.get("motivation");
        BigDecimal requestedAmount = new BigDecimal(String.valueOf(applicationDetails.get("requestedAmount")));
        int fundingYear = (int) applicationDetails.get("fundingYear");
        String applicationStatus = (String) applicationDetails.get("applicationStatus");

        return studentApplicationRepository.updateApplicationDetails(applicationId,
                firstName,
                lastName,
                idNumber,
                gender,
                phoneNumber,
                email,
                ethnicity,
                courseOfStudy,
                departmentName,
                reviewerComment,
                motivation,
                requestedAmount,
                fundingYear,
                applicationStatus);
    }

    @PostMapping("/create-student-application")
    public ResponseEntity<?> createStudentApp(@RequestBody NewStudentApplicationDTO application) {
        try {
            studentApplicationRepository.createApplication(application);
            return ResponseEntity.ok("{\"message\": \"Student application created successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    "{\\\"error\\\": \\\"Failed to create student application,please ensure all fields a valid\"}");
        }
    }

    @PostMapping("/student-documents")
    public ResponseEntity<?> addStudentDocuments(@RequestBody DocumentsDTO documents) {
        try {
            studentApplicationRepository.addDocumentPaths(documents);
            return ResponseEntity.ok("{\"message\": \"Student documents were added successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\\\"error\\\": \\\"Failed to add the documents,please try again or request a new link"
                            + e.getMessage() + "\"}");
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/student-applications-by-hod")
    public ResponseEntity<List<StudentApplicationDTO>> getStudentApplicationsByHODName(@RequestParam String hodName) {
        try {
            List<StudentApplicationDTO> applications = studentApplicationRepository.findByHODName(hodName);
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/student/review-student")
    public ResponseEntity<?> reviewStudentApp(@RequestBody ApplicationReviewDTO applicationReview) {
        try {
            System.out.println(applicationReview);
            studentApplicationRepository.reviewApplication(applicationReview,"student");
            return ResponseEntity.ok("{\"message\": \"Student application review done successfully\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("{\\\"error\\\": \\\"Failed to add review for student application.\"}");
        }
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/university/review-university")
    public ResponseEntity<?> reviewUniversityApp(@RequestBody ApplicationReviewDTO applicationReview) {
        try {
            studentApplicationRepository.reviewApplication(applicationReview,"university");;
            return ResponseEntity.ok("{\"message\": \"University application review done successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\\\"error\\\": \\\"Failed to add review for university application.\"}");
        }
    }

}