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
@AttributeOverride(name = "id", column = @Column(name = "epreuve_help_file_id"))
@Table(name = "epreuve_help_file")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EpreuveHelpFile extends Auditable<String> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "help_file_id", nullable = false)
    private HelpFile helpFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epreuve_id", nullable = false)
    private Epreuve epreuve;
}
