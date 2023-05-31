package istic.m2.project.gofback.controllers.dto;

import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.enums.PrecisionType;
import istic.m2.project.gofback.entities.enums.SessionType;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@With
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpreuveOutDto implements Serializable {
    private Long id;
    private Long disciplineId;
    private String title;
    private Epreuve.Qualification qualification;
    private String helpFileUrl;
    private String exclusion;
    private List<EpreuvePrecisionOutDto> details;
    private SessionType session;

    @Data
    @With
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EpreuvePrecisionOutDto {
        private PrecisionType precisionType;
        private EpreuvePrecisionValueOutDto values;
    }

    @Data
    @With
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EpreuvePrecisionValueOutDto {
        private Double value;
        private List<DisciplineOutDto.DisciplineEpreuveDto> epreuves;
    }
}
