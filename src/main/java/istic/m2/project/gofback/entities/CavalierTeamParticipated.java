package istic.m2.project.gofback.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represent the join table between {@link Cavalier} and {@link Team} which
 * contains the differents cavaliers which belongs to the team
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "cavalier_team_participated_id"))
@Table(name = "cavalier_team_participated")
public class CavalierTeamParticipated extends Auditable<String> {

    @ManyToOne
    @JoinColumn(name = "cavalier_id")
    private Cavalier cavalier;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

}
