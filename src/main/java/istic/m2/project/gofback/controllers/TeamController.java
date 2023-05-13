package istic.m2.project.gofback.controllers;

import io.swagger.v3.oas.annotations.Operation;
import istic.m2.project.gofback.controllers.dto.CreateTeamInDto;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.controllers.dto.TeamOutDto;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.services.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Allow to create team and modify it, according business rules
 */
@RestController()
@RequestMapping(path = "/v1/api/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping("allTeams")
    @Operation(description = "get all teams")
    public ResponseEntity<ResponseDto<ArrayList<TeamOutDto>>> getAllTeam() {
        return ResponseEntity.ok(new ResponseDto<>(new ArrayList<>(teamService.getAllTeams())));
    }

    @PostMapping("create")
    @Operation(description = "Create team")
    public ResponseEntity<ResponseDto<Long>> createTeam(@RequestBody @Valid CreateTeamInDto request) throws BusinessException, IllegalAccessException {

        return ResponseEntity.ok()
                .body(new ResponseDto<>(teamService.createTeam(request)));
    }

}
