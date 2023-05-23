package istic.m2.project.gofback.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.io.Serializable;
import java.util.List;

@With
//TODO firstName, lastName, email,{idepreuve, qualification}
public record LoginOutDto(String accessToken, String refreshToken, UserLoginInfoOutDto user) implements Serializable {
    @Data
    @With
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLoginInfoOutDto {
        private long userId;
        private String firstName;
        private String lastName;
        private String email;
        private List<InscriptionInDto.ChampionShipInscription> epreuves;

    }
}
