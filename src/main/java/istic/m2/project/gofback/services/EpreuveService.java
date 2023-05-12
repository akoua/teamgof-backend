package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.EpreuveInDto;
import istic.m2.project.gofback.controllers.dto.EpreuveOutDto;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.DisciplineRepository;
import istic.m2.project.gofback.repositories.EpreuveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EpreuveService {

    private final DisciplineRepository disciplineRepository;
    private final EpreuveRepository epreuveRepository;

    public EpreuveOutDto addEpreuve(EpreuveInDto epreuve) throws BusinessException {
        var discipline = disciplineRepository.findDisciplineByName(epreuve.discipline())
                .orElseThrow(() -> new BusinessException(MessageError.DISCIPLINE_NOT_FOUND, String.format(" with name %s", epreuve.discipline())));

        var epreuveSave = epreuveRepository.save(new Epreuve()
                .withName(epreuve.name())
                .withSession(epreuve.session())
                .withDiscipline(discipline)
                .withQualification(new Epreuve.Qualification(epreuve.qualification().getQualificationCavalier(),
                        epreuve.qualification().getQualificationEquide())));
        return new EpreuveOutDto(epreuveSave.getName(), epreuveSave.getDiscipline().getName(), epreuveSave.getQualification(), epreuveSave.getSession());
    }
}
