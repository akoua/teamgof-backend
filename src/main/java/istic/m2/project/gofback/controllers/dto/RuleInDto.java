package istic.m2.project.gofback.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Dto for rules
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleInDto {
    private String disciplineName;
    private List<EpreuveRules> rules;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EpreuveRules {
        private List<Long> epreuveIds;
        private MinimalConditions minimalConditions;
        private List<OtherRules> otherRules;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MinimalConditions {
        private List<Long> conditionEpreuveIds;
        private Double currentPercent;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OtherRules {
        private String disciplineName;
        private List<Long> otherRulesEpreuveIds;
    }
}
