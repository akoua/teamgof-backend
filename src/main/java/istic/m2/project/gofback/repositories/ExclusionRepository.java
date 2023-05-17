package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Exclusion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExclusionRepository extends JpaRepository<Exclusion, Long> {
    Optional<Exclusion> findExclusionByLabel(String label);
}
