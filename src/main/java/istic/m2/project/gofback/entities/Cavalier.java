package istic.m2.project.gofback.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "cavalier_id"))
@NoArgsConstructor
@AllArgsConstructor
@With
@Getter
@Setter
public class Cavalier extends Auditable<String>{

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private Date birthDate;

    private String location;
    private String niveau;

    @OneToMany(targetEntity = Poney.class, mappedBy = "cavalier", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Poney> poneys;

    @ManyToMany(targetEntity = Epreuve.class, fetch = FetchType.LAZY, mappedBy = "cavaliersPracticeEpreuve")
    private Set<Epreuve> epreuveCavalierPractice;

    @ManyToMany(targetEntity = Epreuve.class, fetch = FetchType.LAZY, mappedBy = "cavaliersEpreuveParticipated")
    private Set<Epreuve> epreuveCavalierParticipated;

    @ManyToMany(targetEntity = Team.class, fetch = FetchType.LAZY, mappedBy = "cavaliersParticipated")
    private Set<Team> teamBelong;

    @Override
    public String toString() {
        return "Cavalier{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", location='" + location + '\'' +
                ", niveau='" + niveau + '\'' +
                ", poneys=" + poneys +
                ", epreuveCavalierPractice=" + epreuveCavalierPractice +
                ", epreuveCavalierParticipated=" + epreuveCavalierParticipated +
                ", teamBelong=" + teamBelong +
                '}';
    }
}
