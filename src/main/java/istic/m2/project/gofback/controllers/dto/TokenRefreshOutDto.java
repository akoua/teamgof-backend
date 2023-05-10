package istic.m2.project.gofback.controllers.dto;

import java.io.Serializable;

public record TokenRefreshOutDto(String accessToken, String refreshToken) implements Serializable {
    @Override
    public String toString() {
        return "{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
