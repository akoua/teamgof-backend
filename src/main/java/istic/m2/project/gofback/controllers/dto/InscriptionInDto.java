package istic.m2.project.gofback.controllers.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
public class InscriptionInDto {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @Valid
    private List<ChampionShipInscription> epreuves;
    @NotBlank
    private String email;
    @NotBlank
    private String pwd;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @With
    public static class ChampionShipInscription {
        @NotNull
        private Long championshipId;
        @NotNull
        private Integer riderScore;
    }
}
