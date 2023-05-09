package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Poney;
import istic.m2.project.gofback.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
