package istic.m2.project.gofback.controllers.dto;

import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.entities.Precision;
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
    private List<Precision.PrecisionDto> details;
    private SessionType session;
}
