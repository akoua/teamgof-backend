package istic.m2.project.gofback.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.io.Serializable;
import java.util.List;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestedTeamOutDto implements Serializable {
    private TeamOutDto team;
    private String discipline;
    private List<SuggestedTeamEpreuveDto> epreuves;

    @With
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuggestedTeamEpreuveDto {
        private String epreuve;
        private MinimalConditionSuggestedTeam minimalCondition;
        @Schema(description = "the total remaining point to need to be qualified to championship")
        private double remainingPoint;
        @Schema(description = "All championship where rider can be win remaining point")
        private List<ChampionshipSuggestedTeam> championships;
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
