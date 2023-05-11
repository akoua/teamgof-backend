package istic.m2.project.gofback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import istic.m2.project.gofback.entities.enums.TypeMotivation;
import jakarta.persistence.*;
import lombok.*;

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

    private String name;

    @Enumerated(EnumType.STRING)
    private TypeMotivation motivation;

//    private Departement location;

    @ManyToMany(targetEntity = Cavalier.class, fetch = FetchType.LAZY)
    @JoinTable(name = "cavalier_team_participated",
            joinColumns = {@JoinColumn(name = "team_id")},
            inverseJoinColumns = {@JoinColumn(name = "cavalier_id")})
    private Set<Cavalier> cavaliersParticipated;

    @ManyToMany(targetEntity = Cavalier.class, fetch = FetchType.LAZY)
    @JoinTable(name = "epreuve_team_participated",
            joinColumns = {@JoinColumn(name = "team_id")},
            inverseJoinColumns = {@JoinColumn(name = "epreuve_id")})
    private Set<Epreuve> epreuvesParticipated;
}
