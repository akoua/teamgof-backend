package istic.m2.project.gofback.controllers.dto;

import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.enums.SessionType;

import java.io.Serializable;

public record EpreuveOutDto(String name, String discipline,
                            Epreuve.Qualification qualification, SessionType session) implements Serializable {
}
