package istic.m2.project.gofback.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @JoinColumn(name = "cavalier_id", nullable = false)
    private Cavalier cavalier;

    @ManyToOne
    @JoinColumn(name = "epreuve_id", nullable = false)
    private Epreuve epreuve;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Epreuve.Qualification qualification;
}
