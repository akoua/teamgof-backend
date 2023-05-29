package istic.m2.project.gofback.entities;

import istic.m2.project.gofback.entities.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "authority_id"))
public class Authority extends Auditable<String> {

    @Enumerated(EnumType.STRING)
    private RoleType role;
    @ManyToOne
    @JoinColumn(name = "cavalier_id")
    private Cavalier cavalier;
}
