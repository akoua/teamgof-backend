package istic.m2.project.gofback.entities;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.jdbc.JsonJdbcType;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "epreuve_id"))
public class Epreuve extends Auditable<String> {

    private String name;
    @Column
    @JdbcTypeCode(SqlTypes.JSON)
    private Qualification qualification;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;
    @ManyToMany(targetEntity = Cavalier.class, fetch = FetchType.LAZY)
    @JoinTable(name = "cavalier_epreuve_practice",
            joinColumns = {@JoinColumn(name = "epreuve_id")},
    inverseJoinColumns = {@JoinColumn(name = "cavalier_id")})
    private Set<Cavalier> cavaliersPracticeEpreuve;

    @ManyToMany(targetEntity = Cavalier.class, fetch = FetchType.LAZY)
    @JoinTable(name = "cavalier_epreuve_participated",
            joinColumns = {@JoinColumn(name = "epreuve_id")},
    inverseJoinColumns = {@JoinColumn(name = "cavalier_id")})
    private Set<Cavalier> cavaliersEpreuveParticipated;

    @ManyToMany(targetEntity = Team.class, fetch = FetchType.LAZY, mappedBy = "epreuvesParticipated")
    private Set<Team> teamBelong;

    @Override
    public String toString() {
        return "Epreuve{" +
                "name='" + name + '\'' +
                ", qualification=" + qualification +
                ", discipline=" + discipline +
                ", cavaliersPracticeEpreuve=" + cavaliersPracticeEpreuve +
                ", cavaliersEpreuveParticipated=" + cavaliersEpreuveParticipated +
                ", teamBelong=" + teamBelong +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @With
    public static class Qualification{
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

