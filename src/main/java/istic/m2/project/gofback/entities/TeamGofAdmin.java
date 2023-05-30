package istic.m2.project.gofback.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import istic.m2.project.gofback.entities.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Getter
@Setter
@Table(name = "team_gof_admin")
@AttributeOverride(name = "id", column = @Column(name = "team_gof_admin_id"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TeamGofAdmin extends Auditable<String> {
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
