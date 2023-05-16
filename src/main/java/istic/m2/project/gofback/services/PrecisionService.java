package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.PrecisionInDto;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrecisionService {

    private final PrecisionRepository precisionRepository;
    private final EpreuveRepository epreuveRepository;

    public List<Long> addPrecion(PrecisionInDto precisionInDto) throws BusinessException {

        List<Long> epreuveIds = precisionInDto.getPrecisions()
                .stream()
                .flatMap(r -> r.getEpreuveIds().stream())
                .toList();

        //if one epreuve id not exists, we throw exception
//        if (epreuveRepository.verifyIfIdsExists(epreuveIds)
//                .isEmpty()) {
//            throw new BusinessException(MessageError.EPREUVE_NOT_FOUND, String.format("with ids %s", epreuveIds));
//        }

        Set<Epreuve> epreuveSet = epreuveRepository.findAllEpreuveIn(epreuveIds)
                .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.EPREUVE_NOT_FOUND,
                        String.format("with ids %s", epreuveIds)));

        List<Precision> precisionList = new ArrayList<>();

        precisionInDto.getPrecisions()
                .forEach(rule -> rule.getEpreuveIds()
                        .forEach(epreuveId -> epreuveSet.stream()
                                .filter(e -> e.getId().equals(epreuveId))
                                .findFirst()
                                .ifPresent(epreuve -> precisionList.add(new Precision()
                                        .withEpreuve(epreuve)
                                        .withMinimalConditions(rule.getMinimalConditions())
                                        .withOtherRules(rule.getOtherRules())))));
        return precisionRepository.saveAll(precisionList).stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());
    }
}
