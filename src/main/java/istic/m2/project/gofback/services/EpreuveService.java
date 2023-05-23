package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.EpreuveInDto;
import istic.m2.project.gofback.controllers.dto.EpreuveOutDto;
import istic.m2.project.gofback.controllers.dto.EpreuveUpdateInDto;
import istic.m2.project.gofback.entities.*;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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

        return createListEpreuveOutDto(epreuvesSave);
    }

    public EpreuveOutDto getEpreuveInfos(Long championshipId) throws BusinessException {
        Epreuve epreuve = epreuveRepository.findEpreuveAndOtherInfosById(championshipId)
                .orElseThrow(() -> new BusinessException(MessageError.EPREUVE_NOT_FOUND, String.format(" with id %s", championshipId)));

        return createEpreuveOutDto(epreuve);
    }

    @Transactional
    public EpreuveOutDto updateEpreuve(EpreuveUpdateInDto epreuveDto) throws BusinessException {
        Epreuve epreuve = epreuveRepository.findById(epreuveDto.id())
                .orElseThrow(() -> new BusinessException(MessageError.EPREUVE_NOT_FOUND, String.format(" with id %s", epreuveDto.id())));
        Discipline discipline = disciplineRepository.findById(epreuveDto.disciplineId())
                .orElseThrow(() -> new BusinessException(MessageError.DISCIPLINE_NOT_FOUND, String.format(" with id %s", epreuveDto.disciplineId())));

        epreuve.setName(epreuveDto.title());
        epreuve.setDiscipline(discipline);
        epreuve.setSession(epreuveDto.session());
        epreuve.setQualification(epreuveDto.qualification());
        epreuve.setLastModifiedDate(new Date());

        Exclusion exclu = exclusionRepository.findExclusionByLabel(epreuveDto.exclusion()).orElse(null);
        epreuve.getExclusions().stream()
                .findFirst()
                .ifPresentOrElse(exclusion -> {
                            if (!exclusion.getLabel().equals(epreuveDto.exclusion())) {
                                setChampionshipExclusion(epreuve, epreuveDto, exclu);
                            }
                        },
                        () -> setChampionshipExclusion(epreuve, epreuveDto, exclu));

        HelpFile help = helpFileRepository.findHelpFileByUrl(epreuveDto.helpFileUrl()).orElse(null);
        epreuve.getHelpFiles().stream()
                .findFirst()
                .ifPresentOrElse(helpFile -> {
                            if (!helpFile.getUrl().equals(epreuveDto.helpFileUrl())) {
                                setChampionHelpFile(epreuve, epreuveDto, help);
                            }
                        },
                        () -> setChampionHelpFile(epreuve, epreuveDto, help));
        if (null != epreuve.getPrecision()) {
            epreuve.getPrecision().setDetails(epreuveDto.precisions());
        } else {
            epreuve.setPrecision(new Precision().withDetails(epreuveDto.precisions())
                    .withEpreuve(epreuve));
        }


        return createEpreuveOutDto(epreuve);
    }

    private void setChampionshipExclusion(Epreuve epreuve, EpreuveUpdateInDto epreuveDto, Exclusion exclu) {
        if (null != exclu) {
            epreuve.setExclusions(Set.of(exclu));
        } else {
            epreuve.setExclusions(Set.of(new Exclusion().withLabel(epreuveDto.exclusion())));
        }
    }

    private void setChampionHelpFile(Epreuve epreuve, EpreuveUpdateInDto epreuveDto, HelpFile help) {
        epreuve.setLastModifiedDate(new Date());
        if (null != help) {
            epreuve.setHelpFiles(Set.of(help));
        } else {
            epreuve.setHelpFiles(Set.of(new HelpFile().withUrl(epreuveDto.helpFileUrl())));
        }
    }

    private List<EpreuveOutDto> createListEpreuveOutDto(List<Epreuve> epreuves) {
        List<EpreuveOutDto> response = new ArrayList<>();
        epreuves.forEach(epreuve -> response.add(
                createEpreuveOutDto(epreuve)
        ));
        return response;
    }

    private EpreuveOutDto createEpreuveOutDto(Epreuve epreuve) {

        return new EpreuveOutDto()
                .withId(epreuve.getId())
                .withTitle(epreuve.getName())
                .withDisciplineId(epreuve.getDiscipline().getId())
                .withQualification(epreuve.getQualification())
                .withHelpFileUrl(!epreuve.getHelpFiles().isEmpty() ?
                        epreuve.getHelpFiles().stream().map(HelpFile::getUrl).findFirst().orElse("")
                        : "")
                .withExclusion(!epreuve.getExclusions().isEmpty() ?
                        epreuve.getExclusions().stream().map(Exclusion::getLabel).findFirst().orElse("")
                        : "")
                .withDetails(null != epreuve.getPrecision() ? epreuve.getPrecision().getDetails() : null)
                .withSession(epreuve.getSession());
    }
}
