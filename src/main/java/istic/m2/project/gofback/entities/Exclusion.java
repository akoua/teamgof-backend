package istic.m2.project.gofback.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@With
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "exclusion_id"))
public class Exclusion extends Auditable<String> {
    private String label;
    @ManyToMany(targetEntity = Epreuve.class, fetch = FetchType.LAZY, mappedBy = "exclusions")
    private Set<Epreuve> epreuves;
}
