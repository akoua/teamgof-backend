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
    public EpreuveOutDto addEpreuve(EpreuveInDto epreuve) throws BusinessException {
        var discipline = disciplineRepository.findDisciplineByName(epreuve.discipline())
                .orElseThrow(() -> new BusinessException(MessageError.DISCIPLINE_NOT_FOUND, String.format(" with name %s", epreuve.discipline())));

        Exclusion exclusion = exclusionRepository.findExclusionByLabel(epreuve.exclusion())
                .orElse(null);
        HelpFile helpFile = helpFileRepository.findHelpFileByUrl(epreuve.helpFileUrl())
                .orElse(null);


        var epreuveSave = epreuveRepository.save(new Epreuve()
                .withName(epreuve.title())
                .withSession(epreuve.session())
                .withDiscipline(discipline)
                .withQualification(new Epreuve.Qualification(epreuve.qualification().getQualificationCavalier(),
                        epreuve.qualification().getQualificationEquide()))
                .withExclusions(null != exclusion ? Set.of(exclusion) :
                        Set.of(new Exclusion().withLabel(epreuve.exclusion())))
                .withHelpFiles(null != helpFile ? Set.of(helpFile) :
                        Set.of(new HelpFile().withUrl(epreuve.helpFileUrl())))
        );
        precisionRepository.save(new Precision().withDetails(epreuve.details())
                .withEpreuve(epreuveSave));
        return new EpreuveOutDto(epreuveSave.getName(), epreuveSave.getDiscipline().getName(), epreuveSave.getQualification(), epreuveSave.getSession());
    }
}
