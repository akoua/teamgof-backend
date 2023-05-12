package istic.m2.project.gofback.controllers.dto;

import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.enums.SessionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EpreuveInDto(@NotBlank String name, @NotBlank String discipline,
                           @NotNull SessionType session,
                           Epreuve.Qualification qualification) {
}
