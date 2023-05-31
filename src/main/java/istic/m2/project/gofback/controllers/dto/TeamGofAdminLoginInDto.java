package istic.m2.project.gofback.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamGofAdminLoginInDto {
    @NotBlank
    private String email;
    @NotBlank
    private String pwd;
}
