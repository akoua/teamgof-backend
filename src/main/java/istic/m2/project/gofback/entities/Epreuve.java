package istic.m2.project.gofback.entities;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.jdbc.JsonJdbcType;

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
    @ManyToOne(fetch = FetchType.EAGER)
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @With
    public static class Qualification{
        private Integer qualificationCavalier;
        private Integer qualificationEquide;
    }
}

