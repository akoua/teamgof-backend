package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.DisciplineInDto;
import istic.m2.project.gofback.controllers.dto.DisciplineOutDto;
import istic.m2.project.gofback.entities.Discipline;
import istic.m2.project.gofback.repositories.DisciplineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;

    public List<DisciplineOutDto> addDiscipline(DisciplineInDto disciplineInDto) {
        List<Discipline> disciplines = new ArrayList<>();
        disciplineInDto.getNames()
                .forEach(name -> disciplines.add(new Discipline().withName(name)));
        List<Discipline> disciplinesSave = disciplineRepository.saveAll(disciplines);
        return createDisciplineOutDto(disciplinesSave);
    }

    public List<DisciplineOutDto> getAllDisciplineInfos() {
        List<Discipline> disciplines = disciplineRepository.findAllDiscipline().orElse(new ArrayList<>());
        return createDisciplineOutDto(disciplines);
    }

    private List<DisciplineOutDto> createDisciplineOutDto(List<Discipline> disciplines) {
        return disciplines.stream()
                .map(discipline -> new DisciplineOutDto()
                        .withDisciplineId(discipline.getId())
                        .withDisciplineName(discipline.getName())
                        .withChampionships(
                                discipline.getEpreuves().stream()
                                        .map(epreuve -> new DisciplineOutDto.DisciplineEpreuveDto()
                                                .withChampionshipId(epreuve.getId())
                                                .withChampionshipName(epreuve.getName())
                                        ).toList()
                        )
                ).collect(Collectors.toList());
    }
}
