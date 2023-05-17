package istic.m2.project.gofback.entities;

import istic.m2.project.gofback.entities.enums.PrecisionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@With
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "precision_id"))
public class Precision extends Auditable<String> {

    @OneToOne(targetEntity = Epreuve.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "epreuve_id", nullable = false, unique = true)
    private Epreuve epreuve;

    //TODO after merge must be not nullable
    @Column(name = "details", nullable = true)
    @JdbcTypeCode(SqlTypes.JSON)
    private List<PrecisionDto> details;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrecisionDto {
        private PrecisionType precisionType;
        private PrecisionValueDto values;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrecisionValueDto {
        private Double value;
        private List<Long> epreuves;
    }
}
