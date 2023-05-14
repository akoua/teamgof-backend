package istic.m2.project.gofback.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import istic.m2.project.gofback.config.AppConfig;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.CavalierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenService {
    private final AppConfig appConfig;

    private final CavalierRepository cavalierRepository;

    public String generateToken(String email) throws BusinessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwt;
        if (null != authentication) {
            jwt = buildJwt(authentication.getName());
        } else {
            var cavalier = cavalierRepository.findCavalierByEmail(email)
                    .orElseThrow(() -> new BusinessException(MessageError.CAVALIER_NOT_FOUND, String.format(" with email %s", email)));
            jwt = buildJwt(cavalier.getEmail());
        }
        return jwt;
    }

    private String buildJwt(String email) {
        var jwtConfig = appConfig.getSecurity().getJwt();
        SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getJwtKey().getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setIssuer(jwtConfig.getIssuer())
                .setSubject(email)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(jwtConfig.getExpirationTime(), ChronoUnit.SECONDS)))
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }
}
