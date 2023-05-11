package istic.m2.project.gofback.controllers.dto;

import java.util.List;

public class InscriptionInDto {
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
