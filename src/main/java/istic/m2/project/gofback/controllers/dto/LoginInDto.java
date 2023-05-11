package istic.m2.project.gofback.controllers.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginInDto(@NotBlank String email) {
}
