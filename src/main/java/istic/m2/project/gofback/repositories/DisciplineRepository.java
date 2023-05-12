package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//@RepositoryRestResource()
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    Optional<Discipline> findDisciplineByName(String name);
}
