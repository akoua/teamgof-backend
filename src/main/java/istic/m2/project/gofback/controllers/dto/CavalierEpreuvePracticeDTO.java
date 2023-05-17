package istic.m2.project.gofback.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CavalierEpreuvePracticeDTO {
    private Long cavalierId;
    private Long epreuveId;
    private Integer qualificationCavalier;

}
