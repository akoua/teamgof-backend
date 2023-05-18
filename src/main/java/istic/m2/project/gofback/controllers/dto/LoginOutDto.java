package istic.m2.project.gofback.controllers.dto;

import lombok.With;

import java.io.Serializable;

@With
//TODO firstName, lastName, email,{idepreuve, qualification}
public record LoginOutDto(String accessToken, String refreshToken, long userId) implements Serializable {
}
