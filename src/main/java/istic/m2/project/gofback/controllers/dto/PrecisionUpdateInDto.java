package istic.m2.project.gofback.controllers.dto;

import istic.m2.project.gofback.entities.Precision;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Dto for rules
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrecisionUpdateInDto {
    @NotNull
    private List<Precision.PrecisionDto> precisions;
}
