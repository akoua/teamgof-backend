package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.entities.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
}
