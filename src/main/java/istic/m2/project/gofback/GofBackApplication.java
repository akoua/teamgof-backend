package istic.m2.project.gofback;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
//@EnableWebSecurity(debug = true)
public class GofBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(GofBackApplication.class, args);
    }
}
