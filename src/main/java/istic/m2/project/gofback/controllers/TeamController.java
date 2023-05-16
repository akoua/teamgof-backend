package istic.m2.project.gofback.controllers;

import io.swagger.v3.oas.annotations.Operation;
import istic.m2.project.gofback.config.AppConfig;
import istic.m2.project.gofback.controllers.dto.CreateTeamInDto;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.controllers.dto.TeamOutDto;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.ErrorUtils;
import istic.m2.project.gofback.repositories.paging.PagingHelper;
import istic.m2.project.gofback.services.TeamService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Allow to create team and modify it, according business rules
 */
@RestController()
@RequestMapping(path = "/api/v1/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final AppConfig appConfig;

    @GetMapping("allTeams")
    @Operation(description = "get all teams")
    public ResponseEntity<ResponseDto<ArrayList<TeamOutDto>>> getAllTeam(@RequestParam(name = "begin", defaultValue = "0", required = false) Integer begin,
                                                                         @RequestParam(name = "end", defaultValue = "2", required = false) Integer end,
                                                                         HttpServletRequest request) throws BusinessException {

        ErrorUtils.verifyPaginationIndex(begin, end, appConfig);

        var response = teamService.getAllTeams(begin, end, request.getRequestURI());
        if (null != response.getPagination() && PagingHelper.isPartialResponseContent(response.getPagination())) {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(response.getPagination().toHeader())
                    .body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("create")
    @Operation(description = "Create team")
    public ResponseEntity<ResponseDto<Long>> createTeam(@RequestBody @Valid CreateTeamInDto request) throws BusinessException, IllegalAccessException {

        return ResponseEntity.ok()
                .body(new ResponseDto<>(teamService.createTeam(request)));
    }

    @PutMapping("update/{teamId}")
    @Operation(description = "Update Team")
    public ResponseEntity<ResponseDto<TeamOutDto>> updateTeam(@PathVariable Long teamId, @RequestBody @Valid CreateTeamInDto requestUpdate) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(teamService.updateTeam(teamId, requestUpdate)));
    }

}
