package istic.m2.project.gofback.security;

import istic.m2.project.gofback.config.SecurityConfig;
import istic.m2.project.gofback.security.filter.JwtTokenValidatorFilter;
import istic.m2.project.gofback.services.RefreshJwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.time.Duration;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class ProjectSecurity {

    private final SecurityConfig securityConfig;
    private final RefreshJwtTokenService refreshJwtTokenService;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("localhost:4200"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setMaxAge(Duration.ofDays(360));
                    return config;
                }).and()
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/v1/api/cavalier/**"),
                        new AntPathRequestMatcher("/v1/api/login/sign-in/**"))
                .authenticated().and()
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/v1/api/login/sign-up/**"),
                        new AntPathRequestMatcher("/v1/api/token/**"),
                        new AntPathRequestMatcher("/v3/api-docs/**"),
                        new AntPathRequestMatcher("/swagger-ui.html"),
                        new AntPathRequestMatcher("/swagger-ui/**"),
                        new AntPathRequestMatcher("/disciplines/**"),
                        new AntPathRequestMatcher("/epreuves/**"),
                        //TODO must change
                        new AntPathRequestMatcher("/v1/api/epreuves/add/**")
                )
                .permitAll()
                .and()
                .addFilterBefore(new JwtTokenValidatorFilter(securityConfig, refreshJwtTokenService), BasicAuthenticationFilter.class)
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
