package istic.m2.project.gofback.controllers.dto;

import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.entities.Epreuve;

import java.io.Serializable;

public record CavalierEpreuvePracticeOutDto(Cavalier cavalier, Epreuve epreuve,
                                            Epreuve.Qualification qualificationCavalier) implements Serializable {
}
