package istic.m2.project.gofback.controllers.dto;

import istic.m2.project.gofback.entities.Team;
import istic.m2.project.gofback.entities.enums.MotivationType;
import istic.m2.project.gofback.entities.enums.SessionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class TeamOutDto implements Serializable {
    private long id;
    private String name;
    private String description;
    private MotivationType motivation = MotivationType.CONVIVIALITY;
    private String departement;
    private Team.ContactTeam contactTeam;
    @Valid
    private List<CreateTeamInDto.TeamMember> members;
    @Valid
    private List<DisciplineEpreuveTeam> epreuves;
    @NotNull
    private List<SessionType> sessions;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @With
    public static class DisciplineEpreuveTeam {
        private String discipline;
        @NotEmpty
        private List<String> championshipNames;
    }
}
