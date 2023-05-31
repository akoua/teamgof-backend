package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Team;
import istic.m2.project.gofback.repositories.paging.OffsetLimitPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @EntityGraph(attributePaths = {"cavaliersParticipated", "epreuvesParticipated"})
    @Query("select t from #{#entityName} t where t.id = :teamId")
    Optional<Team> findTeamAndCavalierParticipatedAndEpreuveParticipatedById(Long teamId);

    @EntityGraph(attributePaths = {"epreuvesParticipated.discipline"})
    @Query("select t from Team t where t.id = :teamId")
    Optional<Team> findTeamById(Long teamId);

    @Query("select t from Team t ")
    Page<Team> findAllTeamsAndEpreuveWithPagineable(Pageable pageable);

    @EntityGraph(attributePaths = {"epreuvesParticipated", "epreuvesParticipated.discipline"})
    @Query("select t from Team t inner join EpreuveTeamParticipated etp on etp.team.id = t.id")
    Page<Team> findAllTeamsAndEpreuvePagineable(PageRequest of);

    //    @EntityGraph(attributePaths = {"epreuvesParticipated", "epreuvesParticipated.discipline", "epreuvesParticipated.precision"})
    @Query(value = "select t.team_id, t.name, t.departement, t.description, t.motivation, t.members, " +
            "t.created_by, t.created_date, t.last_modified_by, t.last_modified_date from team as t " +
            "inner join epreuve_team_participated as etp on etp.team_id = t.team_id " +
            "inner join epreuve as e on e.epreuve_id  = etp.epreuve_id " +
            "inner join precision as p on p.epreuve_id = e.epreuve_id " +
            "where p.details @> '[{\"precisionType\": \"NUMBER_OF_PARTICIPATED\"}]'",
            nativeQuery = true)
    List<Team> findAllTeamsWhichParticipatedToLimitedChampionship();

    default Page<Team> findAllTeamsAndEpreuvePagineable(Integer begin, Integer size, Sort sort) {
        return findAllTeamsAndEpreuvePagineable(OffsetLimitPageRequest.of(begin, size, sort));
    }


}
