package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.Epreuve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EpreuveRepository extends JpaRepository<Epreuve, Long> {

    @Query(value = "SELECT * FROM epreuve WHERE epreuve_id in (:ids) ")
    Optional<Set<Epreuve>> findAllEpreuveIn(List<Integer> ids);
}
