package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.Poney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoneyRepository extends JpaRepository<Poney, Long> {
}
