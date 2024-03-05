package com.ukukhula.bursaryapi.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ukukhula.bursaryapi.security.MsalValidationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ukukhula.bursaryapi.entities.StudentAllocation;
import com.ukukhula.bursaryapi.repositories.StudentAllocationRepository;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentAllocationController {

    private final StudentAllocationRepository studentAllocationRepository;
    private final MsalValidationService msalValidationService;

    public StudentAllocationController(StudentAllocationRepository studentAllocationRepository, MsalValidationService msalValidationService) {
        this.studentAllocationRepository = studentAllocationRepository;
        this.msalValidationService = msalValidationService;
    }

    @GetMapping("/student/allocation")
    public ResponseEntity<List<StudentAllocation>> getAllStudentAllocations( HttpServletRequest req) {

        List<StudentAllocation> allocations = new ArrayList<>();
        if (req.getHeader("userRole").toLowerCase() == "hod") {
            allocations = studentAllocationRepository.getAllStudentAllocations();
        }
        return ResponseEntity.ok(allocations);
    }

    @GetMapping("student/allocation/{id}")
    public ResponseEntity<StudentAllocation> getStudentAllocationById(@PathVariable int id, HttpServletRequest req) {

        String role = req.getHeader("userRole");
        StudentAllocation allocation = null;
        if (role.toLowerCase() == "hod" || role.toLowerCase() == "admin") {
            allocation = studentAllocationRepository.getStudentAllocationById(id);}
        return allocation != null ? ResponseEntity.ok(allocation) : ResponseEntity.notFound().build();
    }

    @PostMapping("/student/allocation")
    public ResponseEntity<StudentAllocation> createStudentAllocation(@RequestBody StudentAllocation studentAllocation, HttpServletRequest req) {

        String role = req.getHeader("userRole");
        StudentAllocation createdAllocation = null;
        if (role.toLowerCase() == "hod" || role.toLowerCase() == "admin") {
            createdAllocation = studentAllocationRepository.createStudentAllocation(studentAllocation);
        }
        return ResponseEntity.ok(createdAllocation);
    }

    @PutMapping("/student/allocation/{id}")
    public ResponseEntity<StudentAllocation> updateStudentAllocation(
            @PathVariable int id, @RequestBody StudentAllocation updatedAllocation, HttpServletRequest req) {

        String role = req.getHeader("userRole");
        StudentAllocation result = null;

        if (role.equalsIgnoreCase("hod") || role.equalsIgnoreCase("admin")) {
            result = studentAllocationRepository.updateStudentAllocation(id, updatedAllocation);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/student/allocation/{id}")
    public ResponseEntity<Void> deleteStudentAllocation(@PathVariable int id) {
        studentAllocationRepository.deleteStudentAllocation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/allocation/total-spent")
    public ResponseEntity<BigDecimal> getTotalSpentByUniversity(@RequestBody Map<String, Integer> requestBody) {
        int year = requestBody.get("year");
        int universityId = requestBody.get("universityId");

        BigDecimal totalSpent = studentAllocationRepository.getStudentAllocationsTotalSpent(year, universityId);
        return ResponseEntity.ok(totalSpent);
    }
}