package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Team;
import istic.m2.project.gofback.repositories.paging.OffsetLimitPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @EntityGraph(attributePaths = {"epreuvesParticipated", "epreuvesParticipated.discipline"})
    @Query("select t from Team t")
    Page<Team> findAllTeamsAndEpreuvePagineable(PageRequest of);

    default Page<Team> findAllTeamsAndEpreuvePagineable(Integer begin, Integer size, Sort sort) {
        return findAllTeamsAndEpreuvePagineable(OffsetLimitPageRequest.of(begin, size, sort));
    }

}
