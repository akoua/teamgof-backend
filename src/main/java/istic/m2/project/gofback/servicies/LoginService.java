package istic.m2.project.gofback.servicies;

import istic.m2.project.gofback.controllers.inscriptionInDto;
import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.entities.CavalierEpreuvePractice;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.repositories.CavalierEpreuvePracticeRepository;
import istic.m2.project.gofback.repositories.CavalierRepository;
import istic.m2.project.gofback.repositories.EpreuveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final EpreuveRepository epreuveRepository;
    private final CavalierRepository cavalierRepository;
    private final CavalierEpreuvePracticeRepository cavalierEpreuvePracticeRepository;

    public String inscriptionService(inscriptionInDto value) {

        List<CavalierEpreuvePractice> cavalierEpreuvePractices = new ArrayList<>();

        //Vérifier que les champs requis sont remplis
        final List<Integer> listOfIdEpreuve = value.getEpreuve().stream()
                .map(inscriptionInDto.epreuveInscription::getChampionshipId)
                .toList();
        Set<Epreuve> allEpreuveIn = epreuveRepository.findAllEpreuveIn(listOfIdEpreuve)
                .orElseThrow();

        Cavalier cavalier = new Cavalier()
                .withFirstName(value.getFirstname())
                .withLastName(value.getLastname())
                .withEmail(value.getEmail())
                .withPwd(value.getPwd())
                .withEpreuveCavalierPractice(allEpreuveIn);

        allEpreuveIn
                .forEach(e -> value.getEpreuve().stream()
                        .filter(epreuveIns -> epreuveIns.getChampionshipId().equals(e.getId()))
                        .forEach(epreuveIns -> cavalierEpreuvePractices.add(new CavalierEpreuvePractice()
                                .withCavalier(cavalier)
                                .withEpreuve(e)
                                .withQualification(new Epreuve.Qualification()
                                        .withQualificationCavalier(epreuveIns.getRiderscore())
                                        .withQualificationEquide(epreuveIns.getHorseScore())))));


        cavalierRepository.save(cavalier);
        cavalierEpreuvePracticeRepository.saveAll(cavalierEpreuvePractices);


    }
}
