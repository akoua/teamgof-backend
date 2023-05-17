package istic.m2.project.gofback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

/**
 * This class represent the join table between {@link Cavalier} and {@link Epreuve} which
 * contains the qualifications of cavalier and his poney
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "epreuve_exclusion_id"))
@Table(name = "epreuve_exclusion")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EpreuveExclusion extends Auditable<String> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exclusion_id", nullable = false)
    private Exclusion exclusion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epreuve_id", nullable = false)
    private Epreuve epreuve;
}
