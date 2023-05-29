package istic.m2.project.gofback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import istic.m2.project.gofback.controllers.dto.CreateTeamInDto;
import istic.m2.project.gofback.entities.enums.MotivationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Set;

@Entity
@With
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "team_id"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Team extends Auditable<String> {

    @Column(unique = true)
    private String name;

    private String departement;

    private String description;

    @Enumerated(EnumType.STRING)
    private MotivationType motivation;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<CreateTeamInDto.TeamMember> members;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "team_contact")
    private ContactTeam teamContact;

    @ManyToMany(targetEntity = Cavalier.class, fetch = FetchType.LAZY)
    @JoinTable(name = "cavalier_team_participated",
            joinColumns = {@JoinColumn(name = "team_id")},
            inverseJoinColumns = {@JoinColumn(name = "cavalier_id")})
    private Set<Cavalier> cavaliersParticipated;

    @ManyToMany(targetEntity = Epreuve.class, fetch = FetchType.LAZY)
    @JoinTable(name = "epreuve_team_participated",
            joinColumns = {@JoinColumn(name = "team_id")},
            inverseJoinColumns = {@JoinColumn(name = "epreuve_id")})
    private Set<Epreuve> epreuvesParticipated;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @With
    public static class ContactTeam {
        @NotBlank
        private String email;
        @NotBlank
        private String number;
    }
}
