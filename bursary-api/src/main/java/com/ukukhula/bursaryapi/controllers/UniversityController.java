package com.ukukhula.bursaryapi.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ukukhula.bursaryapi.entities.University;

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
import com.ukukhula.bursaryapi.repositories.UniversityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController

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
}
