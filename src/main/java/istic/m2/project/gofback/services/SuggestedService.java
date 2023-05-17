package istic.m2.project.gofback.services;

import istic.m2.project.gofback.entities.CavalierEpreuvePractice;
import istic.m2.project.gofback.entities.EpreuveTeamParticipated;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.ErrorUtils;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.CavalierEpreuvePracticeRepository;
import istic.m2.project.gofback.repositories.CavalierRepository;
import istic.m2.project.gofback.repositories.EpreuveTeamParticipatedRepository;
import istic.m2.project.gofback.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestedService {

    private final CavalierRepository cavalierRepository;
    private final TeamRepository teamRepository;

    private final CavalierEpreuvePracticeRepository cavalierEpreuvePracticeRepository;

    private final EpreuveTeamParticipatedRepository epreuveTeamParticipatedRepository;

    /**
     * Suggest team to cavalier
     *
     * @param idCavalier identification of cavalier
     * @return {@link String}
     */
    public String suggestTeamToCavalier(long idCavalier) throws BusinessException {
        //retrieve cavalier infos and championship he practices

//        Cavalier cavalier = cavalierRepository.findCavalierAndEpreuveCavalierPracticeById(idCavalier)
//                .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.CAVALIER_NOT_FOUND, String.format("with id %s", idCavalier)));
        List<CavalierEpreuvePractice> cavalierEpreuvePractices = cavalierEpreuvePracticeRepository.findCavalierEpreuvePracticeByCavalierId(idCavalier)
                .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.EPREUVE_CAVALIER_PRACTICE_NOT_FOUND, String.format("with cavalier id: %s", idCavalier)));
        //retrieve team infos and championship to compete
        List<Long> epreuvePracticeByCavalierIds = cavalierEpreuvePractices.stream()
                .map(ept -> ept.getEpreuve().getId())
                .toList();
        List<EpreuveTeamParticipated> epreuveTeamWhichPracticeAtLeastOneCavalierChampionship = epreuveTeamParticipatedRepository.findAllTeamsWhereEpreuveParticipatedAtLeastInChampionShip(epreuvePracticeByCavalierIds)
                .orElse(new ArrayList<>());
        //verify if cavalier qualification is greater or equals to championship qualification
        epreuveTeamWhichPracticeAtLeastOneCavalierChampionship
                .forEach(ept -> {
                    cavalierEpreuvePractices
                            .forEach(epreuvePractice -> {
                                if (ept.getEpreuve().equals(epreuvePractice.getEpreuve())) {
                                    if (epreuvePractice.getQualificationCavalier()
                                            .equals(ept.getEpreuve().getQualification().getQualificationCavalier())) {
                                        //verify if qualification respect precision
                                        verifyPrecision(epreuvePractice, epreuveTeamWhichPracticeAtLeastOneCavalierChampionship);
                                    }
                                }
                            });
                });

        return null;
    }

    /**
     * verify if the rider practiced championship, match with championship precision
     *
     * @param cavalierEpreuvePractice {@link CavalierEpreuvePractice} which contains the cavalier qualification
     * @param epreuvesTeam
     */
    private void verifyPrecision(CavalierEpreuvePractice cavalierEpreuvePractice, List<EpreuveTeamParticipated> epreuvesTeam) {

//        Precision precision = cavalierEpreuvePractice.getEpreuve()
//                .getPrecision();
//        List<Long> conditionEpreuveIds = precision.getMinimalConditions()
//                .getConditionEpreuveIds();
//        List<Long> otherRulesEpreuveIds = precision.getOtherRules().stream()
//                .map(Precision.OtherRules::getOtherRulesEpreuveIds)
//                .findFirst()
//                .orElse(new ArrayList<>());
//        List<Epreuve> minimalConditionsEpreuves = new ArrayList<>();
//        List<Epreuve> otherConditionsEpreuves = new ArrayList<>();
//        epreuvesTeam.forEach(etp -> {
//            conditionEpreuveIds.forEach(id -> {
//                if (etp.getEpreuve().getId().equals(id)) {
//                    minimalConditionsEpreuves.add(etp.getEpreuve());
//                }
//            });
//            otherRulesEpreuveIds.forEach(id -> {
//                if (etp.getEpreuve().getId().equals(id)) {
//                    otherConditionsEpreuves.add(etp.getEpreuve());
//                }
//            });
//        });
//        epreuvesTeam.stream()
//                .filter(etp -> conditionEpreuveIds.stream()
//                        .filter(id -> id.equals(etp.getEpreuve().getId()))
//                        .findFirst()
//                        .orElse()
//                )

    }
}
