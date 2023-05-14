package istic.m2.project.gofback.config;

import jakarta.validation.Valid;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties("app")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class AppConfig {

    @Valid
    private int paginationDefaultPageSize;
    @Valid
    private SecurityConfig security;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @With
    @Validated
    public static class SecurityConfig {
        @Valid
        private JwtConfig jwt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @With
    @Validated
    public static class JwtConfig {
        @Valid
        private String jwtKey;
        @Valid
        private String issuer;
        @Valid
        private String header;

        @Valid
        private String headerRefresh;
        @Valid
        private long expirationTime;
        @Valid
        private long refreshTokenExpirationTime;
    }
}
