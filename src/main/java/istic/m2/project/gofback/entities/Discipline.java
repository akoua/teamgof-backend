package istic.m2.project.gofback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "discipline_id"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Discipline extends Auditable<String> {

    @Column(unique = true, nullable = false)
    private String name;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "discipline", orphanRemoval = true)
    private List<Epreuve> epreuves;

    public void setEpreuves(List<Epreuve> epreuves) {
        this.epreuves = epreuves;
        epreuves.forEach(e -> e.setDiscipline(this));
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "name='" + name + '\'' +
                ", epreuves=" + epreuves +
                '}';
    }
}
