package istic.m2.project.gofback.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.Precision;
import istic.m2.project.gofback.entities.enums.SessionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EpreuveUpdateInDto(
        @Schema(description = "The id of championship")
        @NotNull Long id,
        @Schema(description = "The current or new name of championship")
        @NotBlank String title,
        @Schema(description = "The current or new discipline id")
        @NotNull Long disciplineId,
        @NotNull SessionType session,
        String exclusion,
        String helpFileUrl,
        @Valid
        Epreuve.Qualification qualification,
        @NotEmpty
        List<Precision.PrecisionDto> precisions

) {
}
