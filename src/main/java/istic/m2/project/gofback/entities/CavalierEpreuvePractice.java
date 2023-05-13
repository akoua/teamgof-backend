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
@AttributeOverride(name = "id", column = @Column(name = "cavalier_epreuve_practice_id"))
@Table(name = "cavalier_epreuve_practice")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CavalierEpreuvePractice extends Auditable<String> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cavalier_id", nullable = false)
    private Cavalier cavalier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epreuve_id", nullable = false)
    private Epreuve epreuve;

    @Column(name = "qualification_cavalier", nullable = false)
    private Integer qualificationCavalier;
}
