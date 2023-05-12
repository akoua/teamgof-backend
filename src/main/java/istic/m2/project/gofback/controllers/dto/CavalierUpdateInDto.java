package istic.m2.project.gofback.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CavalierUpdateInDto {
    private String firstName;
    private String lastName;
    @NotBlank(message = "email is mandatory")
    private String email;
    private String numberFfe;
    private String description;
    private String location;
    private String niveau;
    @Schema(description = "epreuves which practice by the cavalier")
    @Valid
    private List<InscriptionInDto.ChampionShipInscription> epreuves;

//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Getter
//    @Setter
//    @With
//    public static class CavalierEpreuveUpdateDto {
//        @NotBlank
//        private Long id;
//        private Integer qualificationCavalier = 0;
//    }
}
