package istic.m2.project.gofback.security;

import istic.m2.project.gofback.config.AppConfig;
import istic.m2.project.gofback.security.filter.JwtTokenValidatorFilter;
import istic.m2.project.gofback.services.RefreshJwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class ProjectSecurity {

    private final AppConfig appConfig;
    private final RefreshJwtTokenService refreshJwtTokenService;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .cors()
//                .disable()
                .cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setMaxAge(Duration.ofDays(360));
                    return config;
                }).and()
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/cavalier/**"),
//                        new AntPathRequestMatcher("/api/v1/login/sign-in/**"),
                        new AntPathRequestMatcher("/api/v1/team/create"),
                        new AntPathRequestMatcher("/api/v1/team/update/{teamId}"),
                        new AntPathRequestMatcher("/api/v1/team/delete/{teamId}"),
                        new AntPathRequestMatcher("/api/v1/precisions/**"),
                        new AntPathRequestMatcher("/api/v1/disciplines/add/**"),
                        new AntPathRequestMatcher("/api/v1/disciplines/update/**"),
                        new AntPathRequestMatcher("/api/v1/disciplines/delete/**"),
                        new AntPathRequestMatcher("/api/v1/epreuves/**"),
                        new AntPathRequestMatcher("/api/v1/suggested/**"),
                        new AntPathRequestMatcher("/api/v1/teamGofAdmin/**")
                )
                .authenticated().and()
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/login/**"),
                        new AntPathRequestMatcher("/api/v1/disciplines/all/infos"),
                        new AntPathRequestMatcher("/api/v1/token/**"),
                        new AntPathRequestMatcher("/v3/api-docs/**"),
                        new AntPathRequestMatcher("/swagger-ui.html"),
                        new AntPathRequestMatcher("/swagger-ui/**"),
                        new AntPathRequestMatcher("/disciplines/**"),
                        new AntPathRequestMatcher("/epreuves/**"),
                        new AntPathRequestMatcher("/api/v1/team/allTeams"),
                        //TODO must change
                        new AntPathRequestMatcher("/api/v1/epreuves/add/**")
                )
                .permitAll()
                .and()
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/api/v1/teamGofAdmin/**").hasRole("ADMIN"))
                .addFilterBefore(new JwtTokenValidatorFilter(appConfig, refreshJwtTokenService), BasicAuthenticationFilter.class)
                .httpBasic()
                .authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint())
                .and()
                .build();
    }

//    @Bean
//    GrantedAuthorityDefaults grantedAuthorityDefaults() {
//        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
