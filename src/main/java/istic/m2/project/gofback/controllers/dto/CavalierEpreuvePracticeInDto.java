package istic.m2.project.gofback.controllers.dto;

import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.entities.Epreuve;
import jakarta.validation.constraints.NotBlank;

public record CavalierEpreuvePracticeInDto(@NotBlank Cavalier cavalier, @NotBlank Epreuve epreuve,
                                           Epreuve.Qualification qualificationCavalier) {
}
