package istic.m2.project.gofback.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineUpdateInDto {
    @NotBlank
    private String name;
}
