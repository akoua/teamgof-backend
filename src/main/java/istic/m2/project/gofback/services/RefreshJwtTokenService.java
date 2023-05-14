package istic.m2.project.gofback.services;

import istic.m2.project.gofback.config.AppConfig;
import istic.m2.project.gofback.controllers.dto.TokenRefreshOutDto;
import istic.m2.project.gofback.entities.RefreshToken;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.CavalierRepository;
import istic.m2.project.gofback.repositories.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshJwtTokenService {
    private final AppConfig appConfig;
    private final CavalierRepository cavalierRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenService jwtTokenService;

    public TokenRefreshOutDto refreshToken(String token) throws BusinessException {
        var refreshToken = findByToken(token);
        verifyExpiration(refreshToken);

        return new TokenRefreshOutDto(jwtTokenService.generateToken(refreshToken.getCavalier().getEmail()),
                createRefreshToken(refreshToken.getCavalier().getEmail()).getToken());
    }

    public RefreshToken verifyExpiration(RefreshToken token) throws BusinessException {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
//            refreshTokenRepository.delete(token);
            throw new BusinessException(MessageError.REFRESH_TOKEN_WAS_EXPIRED,
                    String.format("for %s", token));
        }
        return token;
    }

    public RefreshToken createRefreshToken(String email) throws BusinessException {
        var cavalier = cavalierRepository.findCavalierByEmail(email)
                .orElseThrow(() -> new BusinessException(MessageError.CAVALIER_NOT_FOUND, String.format(" with email %s", email)));
        deleteByUserId(cavalier.getId());
        RefreshToken refreshToken = new RefreshToken()
                .withCavalier(cavalier)
                .withToken(UUID.randomUUID().toString())
                .withExpiryDate(Instant.now().plusSeconds(appConfig.getSecurity().getJwt()
                        .getRefreshTokenExpirationTime()));

        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Transactional
    public void deleteByUserId(Long id) {
        refreshTokenRepository.deleteRefreshTokenByCavalierId(id);
    }

    private RefreshToken findByToken(String token) throws BusinessException {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BusinessException(MessageError.REFRESH_TOKEN_NOT_FOUND,
                        String.format("refresh token not found for token %s", token)));
    }
}
