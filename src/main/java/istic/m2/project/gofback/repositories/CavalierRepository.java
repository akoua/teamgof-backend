package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Cavalier;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CavalierRepository extends JpaRepository<Cavalier, Long> {
    //    @EntityGraph(attributePaths = {"epreuveCavalierPractice"})
    Optional<Cavalier> findCavalierByEmailIgnoreCase(String email);

//    @EntityGraph(attributePaths = {"poneys", "epreuveCavalierPractice", "teamBelong"})
//    @Query("select c from Cavalier  c where c.id = :id")
//    Optional<Cavalier> findCavalierByIdWithEntityGraph(Long id);


    @Query("select c from Cavalier c where c.numberFfe in (:ffe)")
    Optional<List<Cavalier>> findCavaliersIdByFfeIn(List<String> ffe);

    @EntityGraph(attributePaths = {"epreuveCavalierPractice", "epreuveCavalierPractice.qualification"})
    @Query("select c from Cavalier c where c.id = :id")
    Optional<Cavalier> findCavalierAndEpreuveCavalierPracticeById(Long id);

}
