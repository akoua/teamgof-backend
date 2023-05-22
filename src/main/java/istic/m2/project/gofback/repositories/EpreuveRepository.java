package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Epreuve;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@RepositoryRestResource()
public interface EpreuveRepository extends JpaRepository<Epreuve, Long> {

    @Query(value = "SELECT e FROM Epreuve e WHERE e.id in (:ids)")
    Optional<List<Epreuve>> findAllEpreuveWhereIdIn(List<Long> ids);

    @EntityGraph(attributePaths = {"precision", "exclusions", "helpFiles"})
    @Query(value = "select e from Epreuve e WHERE e.id = :championshipId")
    Optional<Epreuve> findEpreuveAndOtherInfosById(Long championshipId);

//    @Query(value = "SELECT e FROM Epreuve e WHERE e.id in (:ids)")
//    Optional<List<Epreuve>> findAllEpreuveWhereIdIn(List<Long> ids);

//    @Query(value = "SELECT e.id FROM Epreuve e WHERE e.id not in (:ids)")
//    Optional<List<Long>> verifyIfIdsExists(List<Long> ids);
}
