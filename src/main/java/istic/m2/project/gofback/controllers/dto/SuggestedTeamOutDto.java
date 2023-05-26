package istic.m2.project.gofback.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import istic.m2.project.gofback.entities.enums.MotivationType;
import istic.m2.project.gofback.entities.enums.SessionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestedTeamOutDto implements Serializable {
    private List<SuggestedTeamDto> teams;
    private List<SuggestedTeamDisciplineDto> disciplines;


    @With
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuggestedTeamDto {
        private long id;
        private String name;
        private String description;
        private MotivationType motivation = MotivationType.DECOUVERTE;
        private String departement;
        @Valid
        private List<CreateTeamInDto.TeamMember> members;
        @NotNull
        private Set<SessionType> sessions = new HashSet<>();
    }

    @With
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuggestedTeamDisciplineDto {
        private String discipline;
        private List<SuggestedTeamEpreuveDto> epreuves;
    }

    @With
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuggestedTeamEpreuveDto {
        private String epreuve;
        private MinimalConditionSuggestedTeam minimalCondition;
        @Schema(description = "the total remaining point to need to be qualified to championship")
        @Min(0)
        private double remainingPoint;
        @Schema(description = "All championship where rider can be win remaining point")
        private List<String> championships;
    }

    @With
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MinimalConditionSuggestedTeam {
        private boolean isValid;
        private String reason;
    }

    @With
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChampionshipSuggestedTeam {
        private String discipline;
        private String championship;
    }
}
