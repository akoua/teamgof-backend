package istic.m2.project.gofback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CavalierEpreuveParticipated extends Auditable<String> {

    @ManyToOne
    @JoinColumn(name = "cavalier_id", nullable = false)
    private Cavalier cavalier;

    @ManyToOne
    @JoinColumn(name = "epreuve_id", nullable = false)
    private Epreuve epreuve;

}
