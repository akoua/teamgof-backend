package istic.m2.project.gofback.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import istic.m2.project.gofback.entities.enums.MotivationType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamInDto {
    private String name;
    private String description;
    private MotivationType motivation = MotivationType.DECOUVERTE;
    private String departement;
    @Valid
    private List<TeamMember> members;
    @Valid
    @Schema(description = "")
    private List<DisciplineEpreuveTeam> disciplineEpreuves;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @With
    public static class TeamMember {
        @NotBlank
        private String firstName;
        @NotBlank
        private String lastName;
        @NotBlank
        private String ffe;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class DisciplineEpreuveTeam {
        @NotEmpty(message = "Set at least one championship")
        private List<Long> championshipId;
    }

}
