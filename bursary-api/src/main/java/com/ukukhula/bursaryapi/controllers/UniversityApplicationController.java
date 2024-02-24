package com.ukukhula.bursaryapi.controllers;

import com.ukukhula.bursaryapi.assemblers.UniversityApplicationModelAssembler;
import com.ukukhula.bursaryapi.entities.University;
import com.ukukhula.bursaryapi.entities.UniversityApplication;
import com.ukukhula.bursaryapi.repositories.UniversityApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UniversityApplicationController {

    final UniversityApplicationRepository universityApplicationRepository;

    private final UniversityApplicationModelAssembler assembler;

    public UniversityApplicationController(UniversityApplicationRepository universityApplicationRepository,
            UniversityApplicationModelAssembler assembler) {
        this.universityApplicationRepository = universityApplicationRepository;
        this.assembler = assembler;
    }

    @GetMapping("/universities/application/{id}")
    public EntityModel<UniversityApplication> oneApplication(@PathVariable int id) {

        UniversityApplication universityApplication = universityApplicationRepository.getApplicationByUniversityId(id);
        return assembler.toModel(universityApplication);
    }
}
