package istic.m2.project.gofback.servicies;

import istic.m2.project.gofback.controllers.dto.InscriptionInDto;
import istic.m2.project.gofback.controllers.dto.LoginInDto;
import istic.m2.project.gofback.controllers.dto.LoginOutDto;
import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.entities.CavalierEpreuvePractice;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.repositories.CavalierEpreuvePracticeRepository;
import istic.m2.project.gofback.repositories.CavalierRepository;
import istic.m2.project.gofback.repositories.EpreuveRepository;
import istic.m2.project.gofback.services.JwtTokenService;
import istic.m2.project.gofback.services.RefreshJwtTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final JwtTokenService jwtTokenService;
    private final RefreshJwtTokenService refreshJwtTokenService;

    private final PasswordEncoder passwordEncoder;

    /**
     * Allow to sign-up the cavalier
     */
    public String inscriptionService(InscriptionInDto inscriptionInDto) {

        List<CavalierEpreuvePractice> cavalierEpreuvePractices = new ArrayList<>();

        //Vérifier que les champs requis sont remplis
        final List<Long> listOfIdEpreuve = inscriptionInDto.getEpreuves().stream()
                .map(InscriptionInDto.ChampionShipInscription::getChampionshipId)
                .toList();
        Set<Epreuve> allEpreuveIn = epreuveRepository.findAllEpreuveIn(listOfIdEpreuve)
                .orElseThrow();
        if (!allEpreuveIn.isEmpty()) {
            Cavalier cavalier = new Cavalier()
                    .withFirstName(inscriptionInDto.getFirstname())
                    .withLastName(inscriptionInDto.getLastname())
                    .withEmail(inscriptionInDto.getEmail())
                    .withPwd(passwordEncoder.encode(inscriptionInDto.getPwd()))
                    .withEpreuveCavalierPractice(allEpreuveIn);

            allEpreuveIn
                    .forEach(e -> inscriptionInDto.getEpreuves().stream()
                            .filter(epreuveIns -> epreuveIns.getChampionshipId().equals(e.getId()))
                            .forEach(epreuveIns -> cavalierEpreuvePractices.add(new CavalierEpreuvePractice()
                                    .withCavalier(cavalier)
                                    .withEpreuve(e)
                                    .withQualificationCavalier(epreuveIns.getRiderScore())
                            )));

            cavalierRepository.save(cavalier);
            cavalierEpreuvePracticeRepository.saveAll(cavalierEpreuvePractices);

        }


        return "test";
    }

    /**
     * Allow to sign-in the cavalier
     */
    public LoginOutDto connexionService(@Valid LoginInDto loginInDto) throws BusinessException {
        var refreshToken = refreshJwtTokenService.createRefreshToken(loginInDto.email());
        return new LoginOutDto(jwtTokenService.generateToken(loginInDto.email()),
                refreshToken.getToken(), refreshToken.getCavalier().getId());
    }
}
