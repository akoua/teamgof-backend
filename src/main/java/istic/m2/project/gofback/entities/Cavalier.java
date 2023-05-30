package istic.m2.project.gofback.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import istic.m2.project.gofback.entities.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "cavalier_id"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cavalier extends Auditable<String> {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;
    @Column(name = "number_ffe")
    private String numberFfe;
    private String description;
    private String location;
    private String niveau;
    @ManyToMany(targetEntity = Epreuve.class, fetch = FetchType.LAZY, mappedBy = "cavaliersPracticeEpreuve")
    private Set<Epreuve> epreuveCavalierPractice;

    @ManyToMany(targetEntity = Team.class, fetch = FetchType.LAZY, mappedBy = "cavaliersParticipated")
    private Set<Team> teamBelong;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Override
    public String toString() {
        return "Cavalier{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", numberFfe='" + numberFfe + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", niveau='" + niveau + '\'' +
                '}';
    }
}
