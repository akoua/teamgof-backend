package istic.m2.project.gofback.controllers;

import istic.m2.project.gofback.entities.Discipline;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.repositories.DisciplineRepository;
import istic.m2.project.gofback.repositories.EpreuveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/api/test")
@RequiredArgsConstructor
public class TestController {

    private final DisciplineRepository disciplineRepository;
    @GetMapping
    public ResponseEntity<String> getTest(){
        var epreuve = new Epreuve()
                .withName("Club Elite GP Paire")
                .withQualification(new Epreuve.Qualification()
                        .withQualificationCavalier(15)
                        .withQualificationEquide(20));
        var discipline = new Discipline()
                .withName("Attelage")
                .withEpreuves(List.of(epreuve));
        disciplineRepository.save(discipline);
        return ResponseEntity.ok("Test deploy New");
    }
}
