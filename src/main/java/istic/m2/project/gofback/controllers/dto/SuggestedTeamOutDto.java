package istic.m2.project.gofback.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.io.Serializable;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestedTeamOutDto implements Serializable {
    private String discipline;
}
