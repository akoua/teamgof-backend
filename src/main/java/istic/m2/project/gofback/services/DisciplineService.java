package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.DisciplineInDto;
import istic.m2.project.gofback.controllers.dto.DisciplineOutDto;
import istic.m2.project.gofback.controllers.dto.DisciplineUpdateInDto;
import istic.m2.project.gofback.entities.Discipline;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.ErrorUtils;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.DisciplineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .forEach(name -> disciplines.add(new Discipline().withName(name).withEpreuves(new ArrayList<>())));
        List<Discipline> disciplinesSave = disciplineRepository.saveAll(disciplines);
        return createDisciplineOutDtos(disciplinesSave);
    }

    public List<DisciplineOutDto> getAllDisciplineInfos() {
        List<Discipline> disciplines = disciplineRepository.findAllDiscipline().orElse(new ArrayList<>());
        return createDisciplineOutDtos(disciplines);
    }

    @Transactional
    public DisciplineOutDto updateDiscipline(Long idDiscipline, DisciplineUpdateInDto disciplineInDto) throws BusinessException {
        var discipline = disciplineRepository.findById(idDiscipline)
                .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.DISCIPLINE_NOT_FOUND,
                        String.format("discipline with %id", idDiscipline)));
        discipline.setName(disciplineInDto.getName());
        return createDisciplineOutDto(discipline);
    }

    @Transactional
    public Boolean deleteDiscipline(Long idDiscipline) throws BusinessException {
        try {
            disciplineRepository.deleteById(idDiscipline);
            return true;
        } catch (Exception e) {
            throw new BusinessException(MessageError.ERROR_DATABASE, String.format("delete discipline with id %s", idDiscipline));
        }

    }

    private List<DisciplineOutDto> createDisciplineOutDtos(List<Discipline> disciplines) {
        return disciplines.stream()
                .map(this::createDisciplineOutDto
                ).collect(Collectors.toList());
    }

    private DisciplineOutDto createDisciplineOutDto(Discipline discipline) {
        return new DisciplineOutDto()
                .withDisciplineId(discipline.getId())
                .withDisciplineName(discipline.getName())
                .withChampionships(
                        discipline.getEpreuves().stream()
                                .map(epreuve -> new DisciplineOutDto.DisciplineEpreuveDto()
                                        .withChampionshipId(epreuve.getId())
                                        .withChampionshipName(epreuve.getName())
                                ).toList()
                );
    }
}
