package istic.m2.project.gofback.servicies;

import istic.m2.project.gofback.controllers.dto.InscriptionInDto;
import istic.m2.project.gofback.controllers.dto.LoginInDto;
import istic.m2.project.gofback.controllers.dto.LoginOutDto;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.services.JwtTokenService;
import istic.m2.project.gofback.services.RefreshJwtTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private RefreshJwtTokenService refreshJwtTokenService;

    public String inscriptionService(InscriptionInDto value) {
        return "";
    }

    public LoginOutDto connexionService(@Valid LoginInDto loginInDto) throws BusinessException {
        var refreshToken = refreshJwtTokenService.createRefreshToken(loginInDto.email());
        return new LoginOutDto(jwtTokenService.generateToken(loginInDto.email()),
                refreshToken.getToken(), refreshToken.getCavalier().getId());
    }
}
