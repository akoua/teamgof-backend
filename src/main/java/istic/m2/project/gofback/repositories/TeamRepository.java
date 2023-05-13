package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @EntityGraph(attributePaths = {"epreuvesParticipated", "epreuvesParticipated.discipline"})
    @Query("select t from Team t")
    Optional<List<Team>> findAllTeamsAndEpreuve();
}
