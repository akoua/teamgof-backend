package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.TeamGofAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamGofAdminRepository extends JpaRepository<TeamGofAdmin, Long> {
    Optional<TeamGofAdmin> findTeamGofAdminByEmail(String email);

}
