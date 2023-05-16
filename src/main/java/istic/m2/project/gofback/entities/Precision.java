package istic.m2.project.gofback.entities;

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

    @ManyToOne(targetEntity = Epreuve.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "epreuve_id", nullable = false, unique = true)
    private Epreuve epreuve;
    @Column(name = "minimal_conditions", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private MinimalConditions minimalConditions;

    @Column(name = "other_rules", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private List<OtherRules> otherRules;

    @With
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MinimalConditions {
        private List<Long> conditionEpreuveIds;
        private Double currentPercent;
    }

    @With
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OtherRules {
        private String disciplineName;
        private List<Long> otherRulesEpreuveIds;
    }
}
