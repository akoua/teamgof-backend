package istic.m2.project.gofback.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import java.util.List;

@AllArgsConstructor
@With
@Getter
public class inscriptionInDto {

    private String firstname;
    private String lastname;
    private List<epreuveInscription> epreuve;
    private String email;
    private String pwd;


    @Getter
    public static class epreuveInscription {
        private Integer championshipId;
        private Integer riderscore;
        private Integer horseScore;
    }
}
