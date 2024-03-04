package com.ukukhula.bursaryapi.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ukukhula.bursaryapi.dto.UniversityInfoDTO;
import com.ukukhula.bursaryapi.entities.Department;
import com.ukukhula.bursaryapi.entities.Ethnicity;
import com.ukukhula.bursaryapi.entities.Gender;
import com.ukukhula.bursaryapi.entities.HeadOfDepartment;
import com.ukukhula.bursaryapi.entities.University;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ukukhula.bursaryapi.repositories.UniversityRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UniversityController {

  private final UniversityRepository universityRepository;

  UniversityController(UniversityRepository universityRepository) {

    this.universityRepository = universityRepository;
  }

  @GetMapping("/universities")
  public List<University> all() {

    return universityRepository.getAllUniversities();
  }

  @PostMapping("/universities")
  public Integer newUniversity(@RequestBody Map<String, Object> universityBodyObject) {
    String name = String.valueOf(universityBodyObject.get("name"));
    return universityRepository.addUniversity(name);
  }

  @GetMapping("/universities/{id}")
  public University one(@PathVariable int id) {
    return universityRepository.getUniversityById(id);

  }

  @GetMapping("/department")
  public List<Department> allDepartments() {

    return universityRepository.getAllDepartments();
  }

  @GetMapping("/balance")
  public BigDecimal getUniversityBalance(@RequestParam Map<String, Object> allocationDetails) {
    String universityName = (String) allocationDetails.get("universityName");
    return universityRepository.getUniversityBalance(universityName);
  }

  @GetMapping("/total-student-allocations")
  public BigDecimal getUniversityTotalSpent(@RequestParam Map<String, Object> allocationDetails) {
    String universityName = (String) allocationDetails.get("universityName");
    return universityRepository.getUniversityTotalSpent(universityName);
  }

  @GetMapping("/hod")
  public List<HeadOfDepartment> allHeadDepartments() {

    return universityRepository.getAllHeadOfDepartments();
  }

  @GetMapping("/ethnicity")
  public List<Ethnicity> allEthnicities() {

    return universityRepository.getAllEthnicities();
  }

  @GetMapping("/biological-sex")
  public List<Gender> allGenders() {

    return universityRepository.getAllGenders();
  }

  @GetMapping("/info/{userID}")
  public UniversityInfoDTO getUniversityInfoByUserID(@PathVariable int userID){

    return universityRepository.getUniversityInfoByUserID(userID);

  }

  @GetMapping("/universities/applications/{status}")
  public List<University> getUniversitiesByStatus(@PathVariable int status)
  {
    return universityRepository.getUniversitiesByApplicationStatus(status);
  };

  @GetMapping("/university/hod/{id}")
  public ResponseEntity<?> getUniversityForHOD(@PathVariable int id) {
      String universityName = universityRepository.getUniversityForHOD(id);
      if (universityName != null) {
        String message = "{\"message\": \"" + universityName + "\"}";
        return ResponseEntity.ok(message);
      } else {
          return ResponseEntity.notFound().build();
      }
  }
  
}
