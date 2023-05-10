package istic.m2.project.gofback.controllers.dto;

import lombok.With;

import java.io.Serializable;

@With
//@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record LoginOutDto(String accessToken, String refreshToken, long userId) implements Serializable {
}
