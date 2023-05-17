package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.HelpFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HelpFileRepository extends JpaRepository<HelpFile, Long> {
    Optional<HelpFile> findHelpFileByUrl(String label);
}
