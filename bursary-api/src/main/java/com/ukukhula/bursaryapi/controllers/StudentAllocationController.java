package com.ukukhula.bursaryapi.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ukukhula.bursaryapi.entities.StudentAllocation;
import com.ukukhula.bursaryapi.services.StudentAllocationService;

@Controller
public class StudentAllocationController {

    private final StudentAllocationService studentAllocationRepository;

    public StudentAllocationController(StudentAllocationService studentAllocationRepository) {
        this.studentAllocationRepository = studentAllocationRepository;
    }

    @GetMapping("/student/allocation")
    public ResponseEntity<List<StudentAllocation>> getAllStudentAllocations() {
        List<StudentAllocation> allocations = studentAllocationRepository.getAllStudentAllocations();
        return ResponseEntity.ok(allocations);
    }

    @GetMapping("student/allocation/{id}")
    public ResponseEntity<StudentAllocation> getStudentAllocationById(@PathVariable int id) {
        StudentAllocation allocation = studentAllocationRepository.getStudentAllocationById(id);
        return allocation != null ? ResponseEntity.ok(allocation) : ResponseEntity.notFound().build();
    }

    @PostMapping("/student/allocation")
    public ResponseEntity<StudentAllocation> createStudentAllocation(@RequestBody StudentAllocation studentAllocation) {
        StudentAllocation createdAllocation = studentAllocationRepository.createStudentAllocation(studentAllocation);
        return ResponseEntity.ok(createdAllocation);
    }

    @PutMapping("/student/allocation/{id}")
    public ResponseEntity<StudentAllocation> updateStudentAllocation(
            @PathVariable int id, @RequestBody StudentAllocation updatedAllocation) {
        StudentAllocation result = studentAllocationRepository.updateStudentAllocation(id, updatedAllocation);
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