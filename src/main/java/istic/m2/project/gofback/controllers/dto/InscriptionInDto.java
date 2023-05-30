package istic.m2.project.gofback.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "it's the ffe number of user")
    private String ffe;
    @NotBlank
    private String pwd;
    @NotBlank
    private String location;

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
