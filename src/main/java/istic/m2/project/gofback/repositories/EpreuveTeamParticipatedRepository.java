package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.EpreuveTeamParticipated;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EpreuveTeamParticipatedRepository extends JpaRepository<EpreuveTeamParticipated, Long> {

    @EntityGraph(attributePaths = {"team"})
    @Query("select etp from EpreuveTeamParticipated etp")
    Optional<List<EpreuveTeamParticipated>> findAllTeamsAndEpreuve();

    @EntityGraph(attributePaths = {"team", "team.epreuvesParticipated", "epreuve"})
    @Query("select ept from EpreuveTeamParticipated ept where ept.epreuve.id in (:championshipIds)")
    Optional<List<EpreuveTeamParticipated>> findAllTeamsWhereEpreuveParticipatedAtLeastInChampionShip(List<Long> championshipIds);
}
