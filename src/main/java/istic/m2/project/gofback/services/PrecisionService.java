package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.PrecisionInDto;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.repositories.EpreuveRepository;
import istic.m2.project.gofback.repositories.PrecisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

//        List<Long> epreuveIds = precisionInDto.getPrecisions()
//                .stream()
//                .flatMap(r -> r.getEpreuveIds().stream())
//                .toList();
//
//        Set<Epreuve> epreuveSet = epreuveRepository.findAllEpreuveIn(epreuveIds)
//                .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.EPREUVE_NOT_FOUND,
//                        String.format("with ids %s", epreuveIds)));
//
//        List<Precision> precisionList = new ArrayList<>();
//
//        precisionInDto.getPrecisions()
//                .forEach(rule -> rule.getEpreuveIds()
//                        .forEach(epreuveId -> epreuveSet.stream()
//                                .filter(e -> e.getId().equals(epreuveId))
//                                .findFirst()
//                                .ifPresent(epreuve -> precisionList.add(new Precision()
//                                        .withEpreuve(epreuve)
//                                        .withMinimalConditions(rule.getMinimalConditions())
//                                        .withOtherRules(rule.getOtherRules())))));
//        return precisionRepository.saveAll(precisionList).stream()
//                .map(p -> p.getId())
//                .collect(Collectors.toList());
        return List.of();
    }
}
