package istic.m2.project.gofback.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represent the join table between {@link Cavalier} and {@link Epreuve} which
 * contains the different epreuves which the cavalier participated
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "cavalier_epreuve_participated_id"))
@Table(name = "cavalier_epreuve_participated")
public class CavalierEpreuveParticipated extends Auditable<String> {

    @ManyToOne
    @JoinColumn(name = "cavalier_id")
    private Cavalier cavalier;

    @ManyToOne
    @JoinColumn(name = "epreuve_id")
    private Epreuve epreuve;

}
