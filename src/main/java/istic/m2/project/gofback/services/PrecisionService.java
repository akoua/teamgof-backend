package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.PrecisionInDto;
import istic.m2.project.gofback.entities.Auditable;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.Precision;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.ErrorUtils;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.EpreuveRepository;
import istic.m2.project.gofback.repositories.PrecisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrecisionService {

    private final PrecisionRepository precisionRepository;
    private final EpreuveRepository epreuveRepository;

    /**
     * Create a precision of champioship
     *
     * @param precisionInDto the input dto
     * @return {@link List} of id, of all precisions which are save
     * @throws BusinessException
     */
    public List<Long> addPrecision(PrecisionInDto precisionInDto) throws BusinessException {

        List<Long> epreuveIds = precisionInDto.getEpreuveIds();
        List<Epreuve> epreuveSet = epreuveRepository.findAllEpreuveWhereIdIn(epreuveIds)
                .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.EPREUVE_NOT_FOUND,
                        String.format("with ids %s", epreuveIds)));

        List<Precision> precisionList = new ArrayList<>();
        epreuveSet.forEach(epreuve -> precisionList.add(new Precision()
                .withEpreuve(epreuve)
                .withDetails(precisionInDto.getPrecisions())));

        return precisionRepository.saveAll(precisionList).stream()
                .map(Auditable::getId)
                .collect(Collectors.toList());
    }
}
