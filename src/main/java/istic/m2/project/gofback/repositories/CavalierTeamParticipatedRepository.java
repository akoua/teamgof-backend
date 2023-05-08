package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.CavalierEpreuvePractice;
import istic.m2.project.gofback.entities.CavalierTeamParticipated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CavalierTeamParticipatedRepository extends JpaRepository<CavalierTeamParticipated, Long> {
}
