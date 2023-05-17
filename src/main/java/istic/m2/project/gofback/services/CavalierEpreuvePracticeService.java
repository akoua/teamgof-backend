package istic.m2.project.gofback.services;

import istic.m2.project.gofback.entities.CavalierEpreuvePractice;
import istic.m2.project.gofback.repositories.CavalierEpreuvePracticeRepository;

public class CavalierEpreuvePracticeService {

    private final CavalierEpreuvePracticeRepository repository;

    public CavalierEpreuvePracticeService(CavalierEpreuvePracticeRepository repository) {
        this.repository = repository;
    }

    public CavalierEpreuvePractice getCavalierEpreuvePractice(Long id) {
        return repository.findById(id).orElse(null);
    }

    public CavalierEpreuvePractice addCreateCavalierEpreuvePractice(CavalierEpreuvePractice cavalierEpreuvePractice) {
        return repository.save(cavalierEpreuvePractice);
    }

    public CavalierEpreuvePractice updateCavalierEpreuvePractice(Long id, CavalierEpreuvePractice cavalierEpreuvePractice) {
        CavalierEpreuvePractice existing = repository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setEpreuve(cavalierEpreuvePractice.getEpreuve());
        existing.setQualificationCavalier(cavalierEpreuvePractice.getQualificationCavalier());
        return repository.save(existing);
    }

    public void deleteCavalierEpreuvePractice(Long id) {
        repository.deleteById(id);
    }
}
