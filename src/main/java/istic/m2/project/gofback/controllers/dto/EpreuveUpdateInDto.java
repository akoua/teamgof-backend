package istic.m2.project.gofback.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.Precision;
import istic.m2.project.gofback.entities.enums.SessionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EpreuveUpdateInDto(
        @Schema(description = "You can set a lot title for the championship which are the same characteristics")
        @NotEmpty List<String> titles,
        @NotBlank String discipline,
        @NotNull SessionType session,
        String exclusion,
        String helpFileUrl,
        Epreuve.Qualification qualification,
        List<Precision.PrecisionDto> precisions

) {
}
