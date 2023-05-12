package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.CavalierOwnInfosDtoOut;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.CavalierEpreuvePracticeRepository;
import istic.m2.project.gofback.repositories.CavalierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CavalierService {

    private final CavalierRepository cavalierRepository;
    private final CavalierEpreuvePracticeRepository CavalierEpreuvePracticeRepository;

    public CavalierOwnInfosDtoOut findUserById(Long id) throws BusinessException {


        var cavalierInfosProjections = CavalierEpreuvePracticeRepository.findCavalierByIdAndEpreuvePractice(id)
                .orElseThrow(() -> new BusinessException(MessageError.CAVALIER_NOT_FOUND, String.format(" with id %s", id)));

        CavalierOwnInfosDtoOut infos = new CavalierOwnInfosDtoOut();
        List<CavalierOwnInfosDtoOut.CavalierEpreuve> epreuves = new ArrayList<>();
        cavalierInfosProjections.forEach(cavalierInfos -> {
            if (null == infos.getFirstName()) {
                infos.setId(cavalierInfos.getId());
                infos.setFirstName(cavalierInfos.getFirstName());
                infos.setLastName(cavalierInfos.getLastName());
                infos.setBirthDate(cavalierInfos.getBirthDate());
                infos.setEmail(cavalierInfos.getEmail());
                infos.setNumberFfe(cavalierInfos.getNumberFfe());
                infos.setDescription(cavalierInfos.getDescription());
                infos.setLocation(cavalierInfos.getLocation());
                infos.setNiveau(cavalierInfos.getNiveau());
            }
            epreuves.add(new CavalierOwnInfosDtoOut.CavalierEpreuve()
                    .withQualificationCavalier(cavalierInfos.getQualificationCavalier())
                    .withName(cavalierInfos.getEpreuveName()));


        });
        infos.setEpreuves(epreuves);
        return infos;
    }
}
