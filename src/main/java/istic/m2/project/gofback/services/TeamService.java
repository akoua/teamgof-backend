package istic.m2.project.gofback.services;

import istic.m2.project.gofback.config.AppConfig;
import istic.m2.project.gofback.controllers.dto.CreateTeamInDto;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.controllers.dto.TeamOutDto;
import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.Team;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.ErrorUtils;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.CavalierRepository;
import istic.m2.project.gofback.repositories.EpreuveRepository;
import istic.m2.project.gofback.repositories.TeamRepository;
import istic.m2.project.gofback.repositories.paging.PagingHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final CavalierRepository cavalierRepository;
    private final EpreuveRepository epreuveRepository;
    private final ModelMapper modelMapper;
    private final AppConfig appConfig;

    public Long createTeam(@Valid CreateTeamInDto request) throws BusinessException {


        List<Cavalier> ridersList = findRidersInDatabase(request.getMembers());

        Team team = new Team()
                .withName(request.getName())
                .withDescription(request.getDescription())
                .withDepartement(request.getDepartement())
                .withMotivation(request.getMotivation())
                .withMembers(request.getMembers())
                .withEpreuvesParticipated(findChampionShipsInDatabase(request.getDisciplineEpreuves()));

        setTeamRiders(team, ridersList, request.getMembers());
        return teamRepository.save(team).getId();
    }

    /**
     * gets all teams by pagination
     *
     * @param beginIndex the beginning index
     * @param endIndex   the end index
     * @param url        the url
     * @return an instance {@link List<TeamOutDto>}
     */
    public ResponseDto<ArrayList<TeamOutDto>> getAllTeams(Integer beginIndex, Integer endIndex, String url) {

        int paginationDefaultPageSize = appConfig.getPaginationDefaultPageSize();

        int nbResults = PagingHelper.getNbResults(beginIndex, endIndex, paginationDefaultPageSize);

        Page<Team> allTeamsAndEpreuve = teamRepository.findAllTeamsAndEpreuvePagineable(beginIndex,
                nbResults, Sort.by("createdDate").descending());
        ResponseDto.PagingDto paging = PagingHelper.getPagingInfo(beginIndex, endIndex, nbResults,
                paginationDefaultPageSize, Math.toIntExact(allTeamsAndEpreuve.getTotalElements()),
                url, Team.class.getSimpleName());
        ResponseDto<ArrayList<TeamOutDto>> response = new ResponseDto<>(new ArrayList<>(createTeamOutDtos(allTeamsAndEpreuve.getContent())));
        response.setPagination(paging);
        return response;
    }

    @Transactional
    public TeamOutDto updateTeam(Long teamId, CreateTeamInDto requestUpdate) throws BusinessException {
        Team team = teamRepository.findTeamAndCavalierParticipatedAndEpreuveParticipatedById(teamId)
                .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.TEAM_NOT_FOUND, String.format("with id %s", teamId)));

        List<Cavalier> ridersList = findRidersInDatabase(requestUpdate.getMembers());
        Set<Epreuve> epreuveSet = findChampionShipsInDatabase(requestUpdate.getDisciplineEpreuves());

        team.setName(requestUpdate.getName());
        team.setDescription(requestUpdate.getDescription());
        team.setMotivation(requestUpdate.getMotivation());
        team.setDepartement(requestUpdate.getDepartement());
        team.setMembers(requestUpdate.getMembers());
        team.setEpreuvesParticipated(epreuveSet);

        setTeamRiders(team, ridersList, requestUpdate.getMembers());

        return createTeamDto(team);
    }

    private Set<Epreuve> findChampionShipsInDatabase(List<CreateTeamInDto.DisciplineEpreuveTeam> disciplineEpreuves) throws BusinessException {
        List<Long> championsList = disciplineEpreuves
                .stream()
                .flatMap(ed -> ed.getChampionshipId().stream())
                .toList();
        return epreuveRepository.findAllEpreuveIn(championsList)
                .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.EPREUVE_NOT_FOUND, String.format("with ids %s", championsList)));
    }

    private List<Cavalier> findRidersInDatabase(List<CreateTeamInDto.TeamMember> members) {
        List<String> ffeList = members
                .stream()
                .map(CreateTeamInDto.TeamMember::getFfe)
                .toList();

        return cavalierRepository.findCavaliersIdByFfeIn(ffeList)
                .orElse(new ArrayList<>());
    }

    private void setTeamRiders(Team team, List<Cavalier> ridersList, List<CreateTeamInDto.TeamMember> members) {
        if (ridersList.isEmpty()) {
            team.setMembers(members);
            team.setCavaliersParticipated(Collections.emptySet());
        } else {
            Set<Cavalier> ridersAlreadyExistInDb = ridersList.stream()
                    .filter(rider -> members
                            .stream().anyMatch(teamMember -> teamMember.getFfe().equals(rider.getNumberFfe())))
                    .collect(Collectors.toSet());
            List<CreateTeamInDto.TeamMember> ridersNotAlreadyExist = members.stream()
                    .map(m -> {
                        for (Cavalier c : ridersAlreadyExistInDb) {
                            if (c.getNumberFfe().equals(m.getFfe())) {
                                m = modelMapper.map(c, CreateTeamInDto.TeamMember.class);
                            }
                        }
                        return m;
                    })
                    .toList();

            team.setCavaliersParticipated(ridersAlreadyExistInDb);
            team.setMembers(ridersNotAlreadyExist);
        }
    }

    private List<TeamOutDto> createTeamOutDtos(List<Team> allTeamsAndEpreuve) {
        List<TeamOutDto> teamOutDtos = allTeamsAndEpreuve.stream()
                .map(this::createTeamDto
                )
                .toList();
        return !teamOutDtos.isEmpty() ? teamOutDtos : Collections.emptyList();
    }

    private TeamOutDto createTeamDto(Team team) {

        HashMap<String, List<String>> epreuves = new HashMap<>();
        HashMap<String, List<String>> disciplineAndEpreuves = team.getEpreuvesParticipated()
                .stream()
                .map(e -> {
                    String discipline = e.getDiscipline().getName();
                    if (epreuves.containsKey(discipline)) {
                        epreuves.get(discipline)
                                .add(e.getName());
                    } else {
                        epreuves.put(discipline, new ArrayList<>(List.of(e.getName())));
                    }
                    return epreuves;
                })
                .flatMap(mp -> mp.entrySet().stream())
                .collect(HashMap::new,
                        (map, entries) -> map.put(entries.getKey(), entries.getValue()),
                        (mapIn, mapOut) -> mapOut.putAll(mapIn));

        return new TeamOutDto()
                .withName(team.getName())
                .withDescription(team.getDescription())
                .withDepartement(team.getDepartement())
                .withMotivation(team.getMotivation())
                .withMembers(team.getMembers())
                .withEpreuves(disciplineAndEpreuves.entrySet()
                        .stream()
                        .map(kv -> new TeamOutDto.DisciplineEpreuveTeam()
                                .withDiscipline(kv.getKey())
                                .withChampionshipNames(kv.getValue()))
                        .toList());
    }
}
