package istic.m2.project.gofback.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import istic.m2.project.gofback.entities.Team;
import istic.m2.project.gofback.entities.enums.MotivationType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamInDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private MotivationType motivation = MotivationType.DECOUVERTE;
    private String departement;
    @Valid
    private Team.ContactTeam contactTeam;
    @Valid
    private List<TeamMember> members;
    @NotNull
    @Schema(description = "all championships which team compete")
    private List<Long> championshipIds;

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

}
