package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Discipline;
import istic.m2.project.gofback.entities.Epreuve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpreuveRepository extends JpaRepository<Epreuve, Long> {
}
