package istic.m2.project.gofback.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurity {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable()
                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .and()
                .build();
    }

}
