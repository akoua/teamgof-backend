package istic.m2.project.gofback.services;

import istic.m2.project.gofback.entities.CavalierEpreuvePractice;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.EpreuveTeamParticipated;
import istic.m2.project.gofback.entities.Precision;
import istic.m2.project.gofback.entities.enums.PrecisionType;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.ErrorUtils;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.CavalierEpreuvePracticeRepository;
import istic.m2.project.gofback.repositories.CavalierRepository;
import istic.m2.project.gofback.repositories.EpreuveTeamParticipatedRepository;
import istic.m2.project.gofback.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        List<EpreuveTeamParticipated> epreuveTeamWhichPracticeAtLeastOneCavalierChampionship =
                epreuveTeamParticipatedRepository.findAllTeamsWhereEpreuveParticipatedAtLeastInChampionShip(
                                epreuvePracticeByCavalierIds)
                        .orElse(new ArrayList<>());

//        List<Team> teams = new ArrayList<>();
//
//        epreuveTeamWhichPracticeAtLeastOneCavalierChampionship
//                .forEach(eptpch -> {
//                    if (!teams.contains(eptpch.getTeam())) {
//                        teams.add(eptpch.getTeam());
//                    }
//                });

        //verify if cavalier qualification is greater or equals to championship qualification
        epreuveTeamWhichPracticeAtLeastOneCavalierChampionship
                .forEach(ept -> {
                    cavalierEpreuvePractices
                            .forEach(epreuvePractice -> {
                                if (ept.getEpreuve().equals(epreuvePractice.getEpreuve())) {
                                    //verify if qualification respect precision
                                    verifyPrecision(epreuvePractice, cavalierEpreuvePractices, epreuveTeamWhichPracticeAtLeastOneCavalierChampionship);
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
    private void verifyPrecision(CavalierEpreuvePractice cavalierEpreuvePractice,
                                 List<CavalierEpreuvePractice> allCavalierEpreuvePractice,
                                 List<EpreuveTeamParticipated> epreuvesTeam) {

        // if precision is not exists or empty we compare the cavalier
        // epreuve practice with the championship qualification directly
        Precision precision = cavalierEpreuvePractice.getEpreuve()
                .getPrecision();
        if (null == precision || precision.getDetails().isEmpty()) {
            Integer championshipQualificationCavalier = cavalierEpreuvePractice.getEpreuve()
                    .getQualification().getQualificationCavalier();
            if (cavalierEpreuvePractice.getQualificationCavalier() >= championshipQualificationCavalier) {
                log.info(String.format("Precision is empty but cavalier point" +
                        " is suffisant to compete to championship %s", cavalierEpreuvePractice
                        .getEpreuve().getName().toUpperCase()));
            } else {
                log.info(String.format("Precision is empty but cavalier point" +
                                " is suffisant to compete to championship %s nevertheless it's rest %s point",
                        cavalierEpreuvePractice.getEpreuve().getName().toUpperCase(),
                        (championshipQualificationCavalier - cavalierEpreuvePractice.getQualificationCavalier())));
            }
        } else {
            //if not we use the precision to verify
            log.info(String.format("Precision is not empty"));
            precision.getDetails()
                    .forEach(detail -> {
                        if (PrecisionType.DISCIPLINEPERCENTAGE.equals(detail.getPrecisionType())) {
                            List<Epreuve> epreuvesPercent = epreuvesTeam.stream()
                                    .filter(epreuveTeamParticipated -> {
                                        return detail.getValues().getEpreuves()
                                                .stream().anyMatch(championshipId -> championshipId
                                                        .equals(epreuveTeamParticipated.getEpreuve()
                                                                .getId()));

                                    }).map(EpreuveTeamParticipated::getEpreuve)
                                    .toList();

                            List<CavalierEpreuvePractice> cavalierEpreuvePracticesPercent = allCavalierEpreuvePractice.stream()
                                    .filter(cep -> epreuvesPercent.stream()
                                            .anyMatch(epreuve -> epreuve.getId().equals(cep.getEpreuve().getId())))
                                    .toList();

                            double riderChampionshipQualificationSum = cavalierEpreuvePracticesPercent.stream()
                                    .mapToDouble(CavalierEpreuvePractice::getQualificationCavalier)
                                    .sum();


                            Epreuve currentEpreuve = cavalierEpreuvePractice.getEpreuve();
                            double percentValue = calculateChampionshipPercentPoint(currentEpreuve, detail);

                            doConditionSummary(riderChampionshipQualificationSum, percentValue, currentEpreuve, epreuvesPercent,
                                    "Minimal condition validated for championship  %s",
                                    "Minimal condition not already validated for championship %s but it's rest %s points in %s"
                            );

                        } else if (PrecisionType.EVENTINGTYPES.equals(detail.getPrecisionType())) {

                            List<CavalierEpreuvePractice> otherCavalierEpreuvePracticesPercent = allCavalierEpreuvePractice.stream()
                                    .filter(cep -> detail.getValues().getEpreuves().stream()
                                            .anyMatch(epreuveId -> epreuveId.equals(cep.getEpreuve().getId())))
                                    .toList();

                            double riderChampionshipQualificationSum = otherCavalierEpreuvePracticesPercent.stream()
                                    .mapToDouble(CavalierEpreuvePractice::getQualificationCavalier)
                                    .sum();

                            Epreuve currentEpreuve = cavalierEpreuvePractice.getEpreuve();
                            double percentValue = calculateChampionshipPercentPoint(currentEpreuve, detail);

                            // TODO today we print only the championship which rider practice in which it's can exercice more to complete his/her point
                            doConditionSummary(riderChampionshipQualificationSum, percentValue, currentEpreuve,
                                    otherCavalierEpreuvePracticesPercent.stream()
                                            .map(CavalierEpreuvePractice::getEpreuve).toList(),
                                    "Other condition validated for championship  %s",
                                    "Other condition not already validated for championship %s but it's rest %s points in %s"
                            );
                        } else {
                            log.info(String.format("Precision is of type %s", PrecisionType.FINISHEVENTS));
                        }
                    });
        }
    }

    private void doConditionSummary(double riderChampionshipQualificationSum, double percentValue, Epreuve currentEpreuve,
                                    List<Epreuve> epreuvesPercent, String successMessage, String failedMessage) {
        if (riderChampionshipQualificationSum >= percentValue) {
            log.info(String.format(successMessage, currentEpreuve.getName().toUpperCase()));
        } else {
            log.info(String.format(failedMessage, currentEpreuve
                            .getName().toUpperCase(), (percentValue - riderChampionshipQualificationSum),
                    epreuvesPercent.stream().map(e -> e.getName().toUpperCase()).toList()));
        }
    }

    private double calculateChampionshipPercentPoint(Epreuve currentEpreuve, Precision.PrecisionDto detail) {
        return currentEpreuve.getQualification().getQualificationCavalier() * (detail.getValues().getValue() / 100);
    }
}
