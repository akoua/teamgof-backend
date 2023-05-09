package istic.m2.project.gofback.controllers;

import java.util.List;

public class inscriptionInDto {
    private String firstname;
    private String lastname;
    private List<epreuveInscription> epreuve;
    private String email;
    private String pwd;


    private class epreuveInscription {
        private Integer championshipId;
        private Integer riderscore;
        private Integer horseScore;
    }
}
