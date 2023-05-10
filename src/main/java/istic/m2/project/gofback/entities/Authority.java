package istic.m2.project.gofback.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "authority_id"))
public class Authority extends Auditable<String> {
    private String name;
    @ManyToOne
    @JoinColumn(name = "cavalier_id")
    private Cavalier cavalier;
}
