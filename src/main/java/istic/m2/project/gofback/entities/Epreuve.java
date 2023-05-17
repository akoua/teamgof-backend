package istic.m2.project.gofback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import istic.m2.project.gofback.entities.enums.SessionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "epreuve_id"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Epreuve extends Auditable<String> {

    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SessionType session;
    @Column
    @JdbcTypeCode(SqlTypes.JSON)
    private Qualification qualification;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;
    @ManyToMany(targetEntity = Cavalier.class, fetch = FetchType.LAZY)
    @JoinTable(name = "cavalier_epreuve_practice",
            joinColumns = {@JoinColumn(name = "epreuve_id")},
            inverseJoinColumns = {@JoinColumn(name = "cavalier_id")})
    private Set<Cavalier> cavaliersPracticeEpreuve;
    @ManyToMany(targetEntity = Team.class, fetch = FetchType.LAZY, mappedBy = "epreuvesParticipated")
    private Set<Team> teamBelong;
    @OneToOne(targetEntity = Precision.class, cascade = CascadeType.ALL, mappedBy = "epreuve", orphanRemoval = true)
    private Precision precision;

    @ManyToMany(targetEntity = Exclusion.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "epreuve_exclusion",
            joinColumns = {@JoinColumn(name = "epreuve_id")},
            inverseJoinColumns = {@JoinColumn(name = "exclusion_id")})
    private Set<Exclusion> exclusions;
    @ManyToMany(targetEntity = HelpFile.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "epreuve_help_file",
            joinColumns = {@JoinColumn(name = "epreuve_id")},
            inverseJoinColumns = {@JoinColumn(name = "help_file_id")})
    private Set<HelpFile> helpFiles;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Epreuve other)) return false;
        return Objects.equals(getName(), other.getName()) && Objects.equals(getDiscipline().getId(), other.getDiscipline().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDiscipline().getId());
    }

    @Override
    public String toString() {
        return "Epreuve{" +
                "name='" + name + '\'' +
                ", qualification=" + qualification +
                ", discipline=" + discipline +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @With
    public static class Qualification {
        private Integer qualificationCavalier;
        private Integer qualificationEquide;

        @Override
        public String toString() {
            return "Qualification{" +
                    "qualificationCavalier=" + qualificationCavalier +
                    ", qualificationEquide=" + qualificationEquide +
                    '}';
        }
    }


}

