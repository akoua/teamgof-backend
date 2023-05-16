package istic.m2.project.gofback.controllers;

import istic.m2.project.gofback.entities.CavalierEpreuvePractice;
import istic.m2.project.gofback.repositories.CavalierEpreuvePracticeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/CavalierEpreuvePractice")
@AllArgsConstructor
public class CavalierEpreuvePracticeController {

    @Autowired
    private CavalierEpreuvePracticeRepository repository;

    @GetMapping("infos/{id}")
    public CavalierEpreuvePractice getCavalierEpreuvePractice(@PathVariable("id") Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping("add")
    public CavalierEpreuvePractice addCreateCavalierEpreuvePractice(@RequestBody CavalierEpreuvePractice cavalierEpreuvePractice) {
        return repository.save(cavalierEpreuvePractice);
    }

    @PutMapping("/{id}")
    public CavalierEpreuvePractice updateCavalierEpreuvePractice(@PathVariable("id") Long id, @RequestBody CavalierEpreuvePractice cavalierEpreuvePractice) {
        CavalierEpreuvePractice existing = repository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setCavalier(cavalierEpreuvePractice.getCavalier());
        existing.setEpreuve(cavalierEpreuvePractice.getEpreuve());
        existing.setQualificationCavalier(cavalierEpreuvePractice.getQualificationCavalier());
        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteCavalierEpreuvePractice(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }

}
