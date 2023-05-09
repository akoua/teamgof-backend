package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.CavalierEpreuveParticipated;
import istic.m2.project.gofback.entities.EpreuveTeamParticipated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CavalierEpreuveParticipatedRepository extends JpaRepository<CavalierEpreuveParticipated, Long> {
}
