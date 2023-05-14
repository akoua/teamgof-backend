package istic.m2.project.gofback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import istic.m2.project.gofback.controllers.dto.CreateTeamInDto;
import istic.m2.project.gofback.entities.enums.TypeMotivation;
import jakarta.persistence.*;
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
    private TypeMotivation motivation;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<CreateTeamInDto.TeamMember> members;

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
}
