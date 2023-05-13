package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.CreateTeamInDto;
import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.Team;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.ErrorUtils;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.CavalierRepository;
import istic.m2.project.gofback.repositories.EpreuveRepository;
import istic.m2.project.gofback.repositories.EpreuveTeamParticipatedRepository;
import istic.m2.project.gofback.repositories.TeamRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final CavalierRepository cavalierRepository;
    private final EpreuveRepository epreuveRepository;
    private final EpreuveTeamParticipatedRepository epreuveTeamParticipatedRepository;

    //    @Transactional
    public Long createTeam(@Valid CreateTeamInDto request) throws BusinessException {

        List<Long> championsList = request.getDisciplineEpreuves()
                .stream()
                .flatMap(ed -> ed.getChampionshipId().stream())
                .toList();
        List<String> ffeList = request.getMembers()
                .stream()
                .map(CreateTeamInDto.TeamMember::getFfe)
                .toList();

        List<Cavalier> ridersList = cavalierRepository.findCavaliersIdByFfeIn(ffeList)
                .orElse(null);
        Set<Epreuve> epreuveSet = epreuveRepository.findAllEpreuveIn(championsList)
                .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.EPREUVE_NOT_FOUND, String.format("with ids %s", championsList)));

        Team team = new Team()
                .withName(request.getName())
                .withDescription(request.getDescription())
                .withDepartement(request.getDepartement())
                .withMotivation(request.getMotivation())
                .withMembers(request.getMembers())
                .withEpreuvesParticipated(epreuveSet);

        if (null != ridersList && ridersList.isEmpty()) {
            team.setMembers(request.getMembers());
        } else {
            Set<Cavalier> ridersAlreadyExistInDb = ridersList.stream()
                    .filter(rider -> request.getMembers()
                            .stream().anyMatch(teamMember -> teamMember.getFfe().equals(rider.getNumberFfe())))
                    .collect(Collectors.toSet());
            team.setCavaliersParticipated(ridersAlreadyExistInDb);
//            List<CreateTeamInDto.TeamMember> ridersNotAlreadyExist = members.stream()
//                    .filter(member -> ridersAlreadyExistInDb.stream()
//                            .noneMatch(cavalier -> cavalier.getNumberFfe().equals(member.getFfe())))
//                    .toList();

//            team.setMembers(ridersNotAlreadyExist);
        }
        return teamRepository.save(team).getId();
    }

}
