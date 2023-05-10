package istic.m2.project.gofback.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "poney_id"))
public class Poney extends Auditable<String> {
    private String name;

    //    private String numberCir;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cavalier_id", nullable = false)
    private Cavalier cavalier;
}
