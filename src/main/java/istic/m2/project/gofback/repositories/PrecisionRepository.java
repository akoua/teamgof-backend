package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Precision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecisionRepository extends JpaRepository<Precision, Long> {
}
