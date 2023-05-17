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
@AttributeOverride(name = "id", column = @Column(name = "help_file_id"))
public class HelpFile extends Auditable<String> {
    private String url;
    @ManyToMany(targetEntity = Epreuve.class, cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "helpFiles")
    private Set<Epreuve> epreuves;
}
