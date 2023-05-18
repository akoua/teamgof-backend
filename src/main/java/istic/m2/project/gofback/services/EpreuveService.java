package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.EpreuveInDto;
import istic.m2.project.gofback.controllers.dto.EpreuveOutDto;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.Exclusion;
import istic.m2.project.gofback.entities.HelpFile;
import istic.m2.project.gofback.entities.Precision;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EpreuveService {

    private final DisciplineRepository disciplineRepository;
    private final EpreuveRepository epreuveRepository;
    private final ExclusionRepository exclusionRepository;
    private final HelpFileRepository helpFileRepository;
    private final PrecisionRepository precisionRepository;

    @Transactional
    public List<EpreuveOutDto> addEpreuve(EpreuveInDto epreuve) throws BusinessException {


        var discipline = disciplineRepository.findDisciplineByNameIgnoreCase(epreuve.discipline())
                .orElseThrow(() -> new BusinessException(MessageError.DISCIPLINE_NOT_FOUND, String.format(" with name %s", epreuve.discipline())));

        Exclusion exclusion = exclusionRepository.findExclusionByLabel(epreuve.exclusion())
                .orElse(null);
        HelpFile helpFile = helpFileRepository.findHelpFileByUrl(epreuve.helpFileUrl())
                .orElse(null);
        List<Epreuve> epreuvesToSave = new ArrayList<>();
        epreuve.titles()
                .stream().filter(title -> !title.isEmpty())
                .forEach(title ->
                        epreuvesToSave.add(new Epreuve()
                                .withName(title)
                                .withSession(epreuve.session())
                                .withDiscipline(discipline)
                                .withQualification(new Epreuve.Qualification(epreuve.qualification().getQualificationCavalier(),
                                        epreuve.qualification().getQualificationEquide()))
                                .withExclusions(null != exclusion ? Set.of(exclusion) :
                                        Set.of(new Exclusion().withLabel(epreuve.exclusion())))
                                .withHelpFiles(null != helpFile ? Set.of(helpFile) :
                                        Set.of(new HelpFile().withUrl(epreuve.helpFileUrl())))));


        List<Epreuve> epreuvesSave = epreuveRepository.saveAll(epreuvesToSave);
//        var precision = precisionRepository.save(new Precision().withDetails(epreuve.details())
//                .withEpreuve(epreuveSave));

        return createEpreuveOutDto(epreuvesSave, new Precision());
    }

    private List<EpreuveOutDto> createEpreuveOutDto(List<Epreuve> epreuves, Precision precision) {
        List<EpreuveOutDto> response = new ArrayList<>();
        epreuves.forEach(epreuve -> response.add(
                new EpreuveOutDto()
                        .withId(epreuve.getId())
                        .withTitle(epreuve.getName().toLowerCase())
                        .withDisciplineId(epreuve.getDiscipline().getId())
                        .withQualification(epreuve.getQualification())
                        .withHelpFileUrl(!epreuve.getHelpFiles().isEmpty() ?
                                epreuve.getHelpFiles().stream().map(HelpFile::getUrl).findFirst().orElse("")
                                : "")
                        .withExclusion(!epreuve.getExclusions().isEmpty() ?
                                epreuve.getExclusions().stream().map(Exclusion::getLabel).findFirst().orElse("")
                                : "")
                        .withDetails(precision.getDetails())
                        .withSession(epreuve.getSession())
        ));
        return response;
    }
}
