package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.CavalierOwnInfosDtoOut;
import istic.m2.project.gofback.controllers.dto.CavalierUpdateInDto;
import istic.m2.project.gofback.controllers.dto.InscriptionInDto;
import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.entities.CavalierEpreuvePractice;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.ErrorUtils;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.CavalierEpreuvePracticeRepository;
import istic.m2.project.gofback.repositories.CavalierRepository;
import istic.m2.project.gofback.repositories.EpreuveRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CavalierService {

    private final CavalierRepository cavalierRepository;
    private final CavalierEpreuvePracticeRepository cavalierEpreuvePracticeRepository;
    private final EpreuveRepository epreuveRepository;
    private final ModelMapper modelMapper;

    public CavalierOwnInfosDtoOut findUserById(Long id) throws BusinessException {


        var cavalierInfosProjections = cavalierEpreuvePracticeRepository.findCavalierByIdAndEpreuvePractice(id)
                .orElseThrow(() ->
                        ErrorUtils.throwBusnessException(MessageError.CAVALIER_NOT_FOUND, String.format(" with id %s", id)));

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

    /**
     * Allow us to update the rider informations
     */
    @Transactional
    public CavalierOwnInfosDtoOut updateInfosCavalier(@Valid CavalierUpdateInDto requestUpdate) throws BusinessException, IllegalAccessException {

        List<CavalierEpreuvePractice> cavalierEpreuvePractices = new ArrayList<>();
        List<CavalierEpreuvePractice> cavalierEpreuvePracticesAlready = new ArrayList<>();

        Cavalier cavalier = cavalierRepository.findCavalierByEmail(requestUpdate.getEmail())
                .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.CAVALIER_NOT_FOUND, String.format(" with email %s",
                        requestUpdate.getEmail())));

        cavalier.setFirstName(requestUpdate.getFirstName().isBlank() ? cavalier.getFirstName() : requestUpdate.getFirstName());
        cavalier.setLastName(requestUpdate.getLastName().isBlank() ? cavalier.getLastName() : requestUpdate.getLastName());
        cavalier.setEmail(requestUpdate.getEmail().isBlank() ? cavalier.getEmail() : requestUpdate.getEmail());
        cavalier.setNumberFfe(requestUpdate.getNumberFfe().isBlank() ? cavalier.getNumberFfe() : requestUpdate.getNumberFfe());
        cavalier.setDescription(requestUpdate.getNumberFfe().isBlank() ? cavalier.getNumberFfe() : requestUpdate.getNumberFfe());
        cavalier.setLocation(requestUpdate.getLocation().isBlank() ? cavalier.getLocation() : requestUpdate.getLocation());
        cavalier.setNiveau(requestUpdate.getNiveau().isBlank() ? cavalier.getNiveau() : requestUpdate.getNiveau());

        //we erase all practice epreuve if nothing is set in the dto, otherwise nothing is happened
        if (requestUpdate.getEpreuves().isEmpty()) {
            cavalierEpreuvePracticeRepository.deleteAllByCavalierId(cavalier.getId());
        } else {

            List<Long> epreuveIds = requestUpdate.getEpreuves().stream()
                    .map(InscriptionInDto.ChampionShipInscription::getChampionshipId)
                    .toList();
            Set<Epreuve> allEpreuveIn = epreuveRepository.findAllEpreuveIn(epreuveIds)
                    .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.EPREUVE_NOT_FOUND, String.format("with ids %s", epreuveIds)));

            var epreuveAlreadyAssociated = allEpreuveIn.stream()
                    .filter(epreuve -> cavalier.getEpreuveCavalierPractice().contains(epreuve))
                    .collect(Collectors.toSet());

            allEpreuveIn.removeAll(epreuveAlreadyAssociated);

            allEpreuveIn
                    .forEach(e -> requestUpdate.getEpreuves().stream()
                            .filter(epreuveIns -> epreuveIns.getChampionshipId().equals(e.getId()))
                            .forEach(epreuveIns -> cavalierEpreuvePractices.add(new CavalierEpreuvePractice()
                                    .withCavalier(cavalier)
                                    .withEpreuve(e)
                                    .withQualificationCavalier(epreuveIns.getRiderScore())
                            )));
            epreuveAlreadyAssociated.forEach(e -> {
                var epreuve = requestUpdate.getEpreuves()
                        .stream().filter(ep -> ep.getChampionshipId().equals(e.getId()))
                        .toList();
                cavalierEpreuvePracticesAlready.add(new CavalierEpreuvePractice()
                        .withCavalier(cavalier)
                        .withEpreuve(e)
                        .withQualificationCavalier(epreuve.get(0).getRiderScore()));
                cavalierEpreuvePracticeRepository.updateCavalierEpreuvePractice(e.getId(), epreuve.get(0).getRiderScore());
            });
        }

        cavalierRepository.save(cavalier);
        cavalierEpreuvePracticeRepository.saveAll(cavalierEpreuvePractices);

        return buildCavalierOwnInfos(cavalier, Stream.concat(cavalierEpreuvePractices.stream(),
                cavalierEpreuvePracticesAlready.stream()).toList());
    }


    private CavalierOwnInfosDtoOut buildCavalierOwnInfos(Cavalier cavalier, List<CavalierEpreuvePractice> epreuvePractices) {

        CavalierOwnInfosDtoOut map = modelMapper.map(cavalier, CavalierOwnInfosDtoOut.class);
        List<CavalierOwnInfosDtoOut.CavalierEpreuve> epreuves = new ArrayList<>();
        epreuvePractices.forEach(e -> {
            epreuves.add(new CavalierOwnInfosDtoOut.CavalierEpreuve()
                    .withQualificationCavalier(e.getQualificationCavalier())
                    .withName(e.getEpreuve().getName()));
        });

        map.setEpreuves(epreuves);
        return map;
    }
}
