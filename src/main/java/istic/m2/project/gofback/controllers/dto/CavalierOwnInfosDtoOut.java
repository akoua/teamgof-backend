package istic.m2.project.gofback.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class CavalierOwnInfosDtoOut implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String numberFfe;
    private String description;
    private String location;
    private String niveau;

    @Schema(description = "epreuves which practice by the cavalier")
    private List<CavalierEpreuve> epreuves;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @With
    public static class CavalierEpreuve {
        private String name;
        private Integer qualificationCavalier;
    }
}
