package istic.m2.project.gofback.controllers.dto;

import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.Precision;
import istic.m2.project.gofback.entities.enums.SessionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EpreuveInDto(@NotBlank String title, @NotBlank String discipline,
                           @NotNull SessionType session,
                           String exclusion,
                           String helpFileUrl,
                           Epreuve.Qualification qualification,
                           List<Precision.PrecisionDto> details

) {
}
