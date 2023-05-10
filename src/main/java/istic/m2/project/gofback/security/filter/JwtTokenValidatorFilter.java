package istic.m2.project.gofback.security.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import istic.m2.project.gofback.config.SecurityConfig;
import istic.m2.project.gofback.services.RefreshJwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Allow to validate the jwt token
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    private final SecurityConfig securityConfig;
    private final RefreshJwtTokenService refreshJwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = request.getHeader(securityConfig.getJwt().getHeader());

        if (null != token) {
            String jwt = parseJwt(request);
            SecretKey key = Keys.hmacShaKeyFor(securityConfig.getJwt().getJwtKey().getBytes(StandardCharsets.UTF_8));
            if (null != jwt && validateJwtToken(jwt, key)) {

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(String.valueOf(claims.get("email")),
                                null,
                                null));
            }
//            else {
//                //we generate a new jwt token
//                log.info(String.format("token %s is expired", jwt));
//                var refreshToken = request.getHeader(securityConfig.getJwt().getHeaderRefresh());
//                try {
//                    TokenRefreshOutDto tokenRefreshOutDto = refreshJwtTokenService.refreshToken(refreshToken);
//                    response.setStatus(HttpStatus.I_AM_A_TEAPOT.value());
//                    response.setHeader("token", tokenRefreshOutDto.toString());
//                } catch (BusinessException e) {
//                    log.error(String.format("token %s can be refresh", refreshToken), e);
//                }
//            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().contains("/login");
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    /**
     * Allow to validate the jwt token
     */
    private boolean validateJwtToken(String jwt, SecretKey jwtSecret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(jwt);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
