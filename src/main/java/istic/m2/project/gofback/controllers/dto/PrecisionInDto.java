package istic.m2.project.gofback.controllers.dto;

import istic.m2.project.gofback.entities.Precision;
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
public class PrecisionInDto {
    private List<Long> epreuveIds;
    private List<Precision.PrecisionDto> precisions;


}
