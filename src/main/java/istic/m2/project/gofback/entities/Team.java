package istic.m2.project.gofback.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@With
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "team_id"))
public class Team extends Auditable<String> {

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
