package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Discipline;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@RepositoryRestResource()
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    Optional<Discipline> findDisciplineByNameIgnoreCase(String name);

    @EntityGraph(attributePaths = {"epreuves"})
    @Query("select d from Discipline d")
    Optional<List<Discipline>> findAllDiscipline();
}
