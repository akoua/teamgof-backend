package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.InscriptionInDto;
import istic.m2.project.gofback.controllers.dto.LoginInDto;
import istic.m2.project.gofback.controllers.dto.LoginOutDto;
import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.entities.CavalierEpreuvePractice;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.enums.RoleType;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.ErrorUtils;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.CavalierEpreuvePracticeRepository;
import istic.m2.project.gofback.repositories.CavalierRepository;
import istic.m2.project.gofback.repositories.EpreuveRepository;
import istic.m2.project.gofback.repositories.TeamGofAdminRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final EpreuveRepository epreuveRepository;
    private final CavalierRepository cavalierRepository;
    private final CavalierEpreuvePracticeRepository cavalierEpreuvePracticeRepository;
    private final TeamGofAdminRepository teamGofAdminRepository;
    private final JwtTokenService jwtTokenService;
    private final RefreshJwtTokenService refreshJwtTokenService;

    private final PasswordEncoder passwordEncoder;

    /**
     * Allow to sign-up the cavalier
     */
    @Transactional
    public String inscriptionService(InscriptionInDto inscriptionInDto) throws BusinessException {

        List<CavalierEpreuvePractice> cavalierEpreuvePractices = new ArrayList<>();

        //Vérifier que les champs requis sont remplis
        final List<Long> listOfIdEpreuve = inscriptionInDto.getEpreuves().stream()
                .map(InscriptionInDto.ChampionShipInscription::getChampionshipId)
                .toList();

        List<Epreuve> allEpreuveIn = epreuveRepository.findAllEpreuveWhereIdIn(listOfIdEpreuve)
                .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.EPREUVE_NOT_FOUND, String.format("with ids %s", listOfIdEpreuve)));

        Cavalier cavalier = new Cavalier()
                .withFirstName(inscriptionInDto.getFirstname())
                .withLastName(inscriptionInDto.getLastname())
                .withEmail(inscriptionInDto.getEmail())
                .withPwd(passwordEncoder.encode(inscriptionInDto.getPwd()))
                .withLocation(inscriptionInDto.getLocation())
                .withNumberFfe(inscriptionInDto.getFfe())
                .withRole(RoleType.CAVALIER)
                .withEpreuveCavalierPractice(new HashSet<>(allEpreuveIn));

        Cavalier finalCavalier = cavalier;
        allEpreuveIn
                .forEach(e -> inscriptionInDto.getEpreuves().stream()
                        .filter(epreuveIns -> epreuveIns.getChampionshipId().equals(e.getId()))
                        .forEach(epreuveIns -> cavalierEpreuvePractices.add(new CavalierEpreuvePractice()
                                .withCavalier(finalCavalier)
                                .withEpreuve(e)
                                .withQualificationCavalier(epreuveIns.getRiderScore())
                        )));

        cavalier = cavalierRepository.save(cavalier);
        cavalierEpreuvePracticeRepository.saveAll(cavalierEpreuvePractices);

        return cavalier.getId().toString();
    }

    /**
     * Allow to sign-in the cavalier
     */
    public LoginOutDto connexionService(@Valid LoginInDto loginInDto) throws BusinessException {
        var refreshToken = refreshJwtTokenService.createRefreshToken(loginInDto.email());
        var cavalier = refreshToken.getCavalier();
        List<CavalierEpreuvePractice> cavalierEpreuvePractices = cavalierEpreuvePracticeRepository.findCavalierEpreuvePracticeByCavalierId(cavalier.getId())
                .orElseThrow(() -> ErrorUtils.throwBusnessException(MessageError.EPREUVE_CAVALIER_PRACTICE_NOT_FOUND, String.format("with cavalier id %s", cavalier.getId())));
        return new LoginOutDto(jwtTokenService.generateToken(loginInDto.email()),
                refreshToken.getToken(), new LoginOutDto.UserLoginInfoOutDto()
                .withUserId(cavalier.getId())
                .withFirstName(cavalier.getFirstName())
                .withLastName(cavalier.getLastName())
                .withEmail(cavalier.getEmail())
                .withRole(cavalier.getRole())
                .withEpreuves(cavalierEpreuvePractices
                        .stream()
                        .map(etp -> new InscriptionInDto.ChampionShipInscription()
                                .withChampionshipId(etp.getEpreuve().getId())
                                .withRiderScore(etp.getQualificationCavalier()))
                        .collect(Collectors.toList())
                )
        );
    }
}
