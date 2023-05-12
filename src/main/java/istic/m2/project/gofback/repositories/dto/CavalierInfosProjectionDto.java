package istic.m2.project.gofback.repositories.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class CavalierInfosProjectionDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String numberFfe;
    private String description;
    private String location;
    private String niveau;
    private Integer qualificationCavalier;
    private String epreuveName;
}
