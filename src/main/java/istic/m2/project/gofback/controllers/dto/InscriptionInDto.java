package istic.m2.project.gofback.controllers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    @Setter
    public static class ChampionShipInscription {
        @NotNull
        private Long championshipId;
        private Integer riderScore;
    }
}
