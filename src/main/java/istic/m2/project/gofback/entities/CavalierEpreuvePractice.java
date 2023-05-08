package istic.m2.project.gofback.entities;

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
public class CavalierEpreuvePractice extends Auditable<String> {

    @ManyToOne
    @JoinColumn(name = "cavalier_id")
    private Cavalier cavalier;

    @ManyToOne
    @JoinColumn(name = "epreuve_id")
    private Epreuve epreuve;

    @Column(name = "cavalier_qualification")
    private Double cavalierQualification;

    @Column(name = "equidé_qualification")
    private Double equideQualification;
}
