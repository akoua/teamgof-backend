package istic.m2.project.gofback.controllers;

import io.swagger.v3.oas.annotations.Operation;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.controllers.dto.SuggestedTeamOutDto;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.services.SuggestedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/v1/suggested")
@RequiredArgsConstructor
public class SuggestedController {

    private final SuggestedService suggestedService;

    @GetMapping("/{idUser}")
    @Operation(description = "suggested team according cavalier infos")
    public ResponseEntity<ResponseDto<ArrayList<SuggestedTeamOutDto>>> suggestedTeamToCavalier(@PathVariable long idUser) throws BusinessException {
        return ResponseEntity.ok(new ResponseDto<>(new ArrayList<>(suggestedService.suggestTeamToCavalier(idUser))));
    }
}
