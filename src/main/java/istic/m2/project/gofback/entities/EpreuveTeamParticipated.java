package istic.m2.project.gofback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@AttributeOverride(name = "id", column = @Column(name = "epreuve_team_participated_id"))
@Table(name = "epreuve_team_participated")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EpreuveTeamParticipated extends Auditable<String> {

    @ManyToOne
    @JoinColumn(name = "epreuve_id", nullable = false)
    private Epreuve epreuve;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

}
