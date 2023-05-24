package istic.m2.project.gofback.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.io.Serializable;
import java.util.List;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineOutDto implements Serializable {
    private long disciplineId;
    private String disciplineName;
    private List<DisciplineEpreuveDto> championships;

    @Data
    @With
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DisciplineEpreuveDto {
        private long championshipId;
        private String championshipName;
    }
}
