package istic.m2.project.gofback.controllers.dto;

import istic.m2.project.gofback.entities.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.io.Serializable;

@With
//TODO firstName, lastName, email,{idepreuve, qualification}
public record LoginAdminOutDto(String accessToken, String refreshToken,
                               AdminLoginInfoOutDto user) implements Serializable {
    @Data
    @With
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminLoginInfoOutDto {
        private long userId;
        private String email;
        private RoleType role;

    }
}
