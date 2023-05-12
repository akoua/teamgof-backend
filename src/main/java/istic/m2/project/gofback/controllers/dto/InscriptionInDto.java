package istic.m2.project.gofback.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class InscriptionInDto {
    private String firstname;
    private String lastname;
    private List<ChampionShipInscription> epreuves;
    private String email;
    private String pwd;


    @Getter
    public static class ChampionShipInscription {
        private Long championshipId;
        private Integer riderScore;
    }
}
