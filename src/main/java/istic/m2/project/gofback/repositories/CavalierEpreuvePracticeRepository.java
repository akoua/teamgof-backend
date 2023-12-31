package istic.m2.project.gofback.repositories;

import istic.m2.project.gofback.entities.CavalierEpreuvePractice;
import istic.m2.project.gofback.repositories.dto.CavalierInfosProjectionDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CavalierEpreuvePracticeRepository extends JpaRepository<CavalierEpreuvePractice, Long> {

    @EntityGraph(attributePaths = {"epreuve"})
    @Query("SELECT new istic.m2.project.gofback.repositories.dto.CavalierInfosProjectionDto(c.id, c.firstName, c.lastName, c.birthDate, c.email, c.numberFfe, c.description, c.location, c.niveau, cev.qualificationCavalier, cev.epreuve.name) from Cavalier c JOIN CavalierEpreuvePractice cev on cev.cavalier.id = c.id " +
            "WHERE c.id = :id ")
    Optional<List<CavalierInfosProjectionDto>> findCavalierByIdAndEpreuvePractice(Long id);

    @EntityGraph(attributePaths = {"epreuve", "epreuve.discipline"})
    @Query("select cep from CavalierEpreuvePractice cep join Cavalier c on c.id = cep.cavalier.id " +
            "where cep.cavalier.id = :cavalierId")
    Optional<List<CavalierEpreuvePractice>> findCavalierEpreuvePracticeByCavalierId(Long cavalierId);

    @Modifying
    @Query("update CavalierEpreuvePractice c SET c.qualificationCavalier = :newValue WHERE (c.epreuve.id =:idChampionship or c.cavalier.id =:idCavalier) ")
    void updateCavalierEpreuvePractice(Long idChampionship, Long idCavalier, Integer newValue);

    int deleteAllByCavalierId(Long id);

    @Modifying
    @Query("delete from CavalierEpreuvePractice cev where cev.id in (:idsCavalierEpreuvePractice)")
    int deleteAllCavalierEpreuvePracticeByIds(List<Long> idsCavalierEpreuvePractice);
}
