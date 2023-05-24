package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.SuggestedTeamOutDto;
import istic.m2.project.gofback.entities.*;
import istic.m2.project.gofback.entities.enums.PrecisionType;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.ErrorUtils;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.CavalierEpreuvePracticeRepository;
import istic.m2.project.gofback.repositories.EpreuveTeamParticipatedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuggestedService {

    private final ModelMapper modelMapper;
    private final CavalierEpreuvePracticeRepository cavalierEpreuvePracticeRepository;
    private final EpreuveTeamParticipatedRepository epreuveTeamParticipatedRepository;

    /**
     * Suggest team to cavalier
     *
     * @param idCavalier identification of cavalier
     * @return {@link String}
     */
    public List<SuggestedTeamOutDto> suggestTeamToCavalier(long idCavalier) throws BusinessException {
        List<SuggestedTeamOutDto> suggestedTeamOutDtos = new ArrayList<>();
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

        List<Team> teams = new ArrayList<>();

        epreuveTeamWhichPracticeAtLeastOneCavalierChampionship
                .forEach(eptpch -> {
                    if (!teams.contains(eptpch.getTeam())) {
                        teams.add(eptpch.getTeam());
                    }
                });

        //verify if cavalier qualification is greater or equals to championship qualification

        cavalierEpreuvePractices.forEach(cep -> {
            var suggestedTeamOutDto = new SuggestedTeamOutDto()
                    .withDisciplines(new ArrayList<>())
                    .withTeams(new ArrayList<>());
            //we filter all team which compete to championship in which rider practice
            List<Team> teamsWhichCompeteToChampionshipPracticedByRider = teams.stream()
                    .filter(team -> team.getEpreuvesParticipated().stream()
                            .anyMatch(epreuve -> epreuve.equals(cep.getEpreuve())))
                    .map(team -> {
//                        if (null == suggestedTeamOutDto.getTeams()) {
//                            suggestedTeamOutDto.setTeams(new ArrayList<>(List.of(modelMapper.map(team, SuggestedTeamOutDto.SuggestedTeamDto.class))));
//                        } else {
//                            suggestedTeamOutDto.getTeams().add(modelMapper.map(team, SuggestedTeamOutDto.SuggestedTeamDto.class));
//                        }
                        suggestedTeamOutDto.getTeams().add(modelMapper.map(team, SuggestedTeamOutDto.SuggestedTeamDto.class));
                        return team;
                    })
                    .toList();
            //all championships wherein teams compete
            List<Epreuve> teamschampionships = teamsWhichCompeteToChampionshipPracticedByRider.stream()
                    .flatMap(team -> team.getEpreuvesParticipated().stream())
                    .toList();

            suggestedTeamOutDtos.add(suggestedTeamOutDto);

            //verify if qualification respect precision for each cavalier championship practice
            verifyPrecision(cep, cavalierEpreuvePractices, teamschampionships, suggestedTeamOutDto);
        });
        return suggestedTeamOutDtos;
    }

    /**
     * verify if the rider practiced championship, match with championship precision
     *
     * @param cavalierEpreuvePractice {@link CavalierEpreuvePractice} which contains the cavalier qualification
     * @param epreuvesTeam
     * @param suggestedTeamOutDto
     */
    private void verifyPrecision(CavalierEpreuvePractice cavalierEpreuvePractice,
                                 List<CavalierEpreuvePractice> allCavalierEpreuvePractice,
                                 List<Epreuve> epreuvesTeam, SuggestedTeamOutDto suggestedTeamOutDto) {

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

                        List<CavalierEpreuvePractice> otherCavalierEpreuvePracticesPercent = allCavalierEpreuvePractice.stream()
                                .filter(cep -> detail.getValues().getEpreuves().stream()
                                        .anyMatch(epreuveId -> epreuveId.equals(cep.getEpreuve().getId())))
                                .toList();

                        if (PrecisionType.DISCIPLINEPERCENTAGE.equals(detail.getPrecisionType())) {
                            List<Epreuve> epreuvesPercent = epreuvesTeam.stream()
                                    .filter(epreuveTeamParticipated -> {
                                        return detail.getValues().getEpreuves()
                                                .stream().anyMatch(championshipId -> championshipId
                                                        .equals(epreuveTeamParticipated.getId()));

                                    }).toList();

                            List<CavalierEpreuvePractice> cavalierEpreuvePracticesPercent = allCavalierEpreuvePractice.stream()
                                    .filter(cep -> epreuvesPercent.stream()
                                            .anyMatch(epreuve -> epreuve.getId().equals(cep.getEpreuve().getId())))
                                    .toList();

                            double riderChampionshipQualificationSum = cavalierEpreuvePracticesPercent.stream()
                                    .mapToDouble(CavalierEpreuvePractice::getQualificationCavalier)
                                    .sum();


                            Epreuve currentEpreuve = cavalierEpreuvePractice.getEpreuve();
                            double percentValue = calculateChampionshipPercentPoint(currentEpreuve, detail);

                            Map<Boolean, String> message = doConditionSummary(riderChampionshipQualificationSum, percentValue, currentEpreuve, epreuvesPercent,
                                    "Minimal condition validated for championship %s",
                                    "Minimal condition not already validated for championship %s but it's rest %s points in %s"
                            );
                            if (suggestedTeamOutDto.getDisciplines().isEmpty()) {
                                log.info("Minimal condition empty");
                                suggestedTeamOutDto.getDisciplines().add(
                                        setSuggestedPrecisionWhenItIsMinimalCondition(new SuggestedTeamOutDto.SuggestedTeamDisciplineDto(),
                                                currentEpreuve, message));
                            } else {
                                log.info("Minimal condition not empty");
//                                suggestedTeamOutDto.getDisciplines()
//                                                .stream()
//                                                        .filter(std -> {
//                                                            std.getDiscipline()
//                                                        })
                                setSuggestedPrecisionWhenItIsMinimalCondition(new SuggestedTeamOutDto.SuggestedTeamDisciplineDto(),
                                        currentEpreuve, message);
                            }
//                            suggestedTeamOutDto.getDisciplines().add(
//                                    new SuggestedTeamOutDto.SuggestedTeamDisciplineDto()
//                                            .withDiscipline(currentEpreuve.getDiscipline().getName())
//                                            .withEpreuves(List.of(new SuggestedTeamOutDto.SuggestedTeamEpreuveDto()
//                                                    .withEpreuve(currentEpreuve.getName())
//                                                    .withMinimalCondition(new SuggestedTeamOutDto.MinimalConditionSuggestedTeam()
//                                                            .withValid(message.keySet().stream().findFirst().orElse(false))
//                                                            .withReason(message.values().stream().findFirst()
//                                                                    .orElse("error during computing")))
//                                                    .withRemainingPoint((percentValue - riderChampionshipQualificationSum))
//                                                    .withChampionships(otherCavalierEpreuvePracticesPercent.stream()
//                                                            .map(cep -> cep.getEpreuve().getName()
//                                                                    .toUpperCase()).toList())
//                                            )));

                        } else if (PrecisionType.EVENTINGTYPES.equals(detail.getPrecisionType())) {

                            double riderChampionshipQualificationSum = otherCavalierEpreuvePracticesPercent.stream()
                                    .mapToDouble(CavalierEpreuvePractice::getQualificationCavalier)
                                    .sum();

                            Epreuve currentEpreuve = cavalierEpreuvePractice.getEpreuve();
                            double percentValue = calculateChampionshipPercentPoint(currentEpreuve, detail);

                            // TODO today we print only the championship which rider practice in which it's can exercice more to complete his/her point
                            Map<Boolean, String> message = doConditionSummary(riderChampionshipQualificationSum, percentValue, currentEpreuve,
                                    otherCavalierEpreuvePracticesPercent.stream()
                                            .map(CavalierEpreuvePractice::getEpreuve).toList(),
                                    "Other condition validated for championship  %s",
                                    "Other condition not already validated for championship %s but it's rest %s points in %s"
                            );
                            if (suggestedTeamOutDto.getDisciplines().isEmpty()) {
                                log.info("Other condition empty");
                            } else {
                                log.info("Other condition not empty");
                            }
                            suggestedTeamOutDto.getDisciplines().add(
                                    new SuggestedTeamOutDto.SuggestedTeamDisciplineDto()
                                            .withDiscipline(currentEpreuve.getDiscipline().getName())
                                            .withEpreuves(List.of(new SuggestedTeamOutDto.SuggestedTeamEpreuveDto()
                                                    .withEpreuve(currentEpreuve.getName())
                                                    .withMinimalCondition(new SuggestedTeamOutDto.MinimalConditionSuggestedTeam()
                                                            .withValid(message.keySet().stream().findFirst().orElse(false))
                                                            .withReason(message.values().stream().findFirst()
                                                                    .orElse("error during computing")))
                                                    .withRemainingPoint((percentValue - riderChampionshipQualificationSum))
                                                    .withChampionships(otherCavalierEpreuvePracticesPercent.stream()
                                                            .map(cep -> cep.getEpreuve().getName()
                                                                    .toUpperCase()).toList())
                                            )));
                        } else {
                            log.info(String.format("Precision is of type %s", PrecisionType.FINISHEVENTS));
                        }
                    });
        }
    }

    private SuggestedTeamOutDto.SuggestedTeamDisciplineDto setSuggestedPrecisionWhenItIsMinimalCondition(SuggestedTeamOutDto.SuggestedTeamDisciplineDto suggestedTeamDisciplineDto
            , Epreuve currentEpreuve, Map<Boolean, String> message) {

        suggestedTeamDisciplineDto.setDiscipline(currentEpreuve.getDiscipline().getName());
        suggestedTeamDisciplineDto.setEpreuves(List.of(new SuggestedTeamOutDto.SuggestedTeamEpreuveDto()
                .withEpreuve(currentEpreuve.getName())
                .withMinimalCondition(new SuggestedTeamOutDto.MinimalConditionSuggestedTeam()
                        .withValid(message.keySet().stream().findFirst().orElse(false))
                        .withReason(message.values().stream().findFirst()
                                .orElse("error during computing")))
        ));
        return suggestedTeamDisciplineDto;
    }

    private Map<Boolean, String> doConditionSummary(double riderChampionshipQualificationSum, double percentValue, Epreuve currentEpreuve,
                                                    List<Epreuve> epreuvesPercent, String successMessage, String failedMessage) {

        Map<Boolean, String> message = new HashMap<>();

        if (riderChampionshipQualificationSum >= percentValue) {
            var msg = String.format(successMessage, currentEpreuve.getName().toUpperCase());
            message.put(true, msg);
            log.info(msg);
        } else {
            var msg = String.format(failedMessage, currentEpreuve
                            .getName().toUpperCase(), (percentValue - riderChampionshipQualificationSum),
                    epreuvesPercent.stream().map(e -> e.getName().toUpperCase()).toList());
            message.put(true, msg);
            log.info(msg);
        }
        return message;
    }

    private double calculateChampionshipPercentPoint(Epreuve currentEpreuve, Precision.PrecisionDto detail) {
        return currentEpreuve.getQualification().getQualificationCavalier() * (detail.getValues().getValue() / 100);
    }
}
