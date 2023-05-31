package istic.m2.project.gofback.controllers;

import io.swagger.v3.oas.annotations.Operation;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.controllers.dto.TeamGofAdminLoginInDto;
import istic.m2.project.gofback.entities.enums.RoleType;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.services.TeamGofAdminService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/teamGofAdmin")
@RolesAllowed(value = {RoleType.RoleConstant.ADMIN})
@RequiredArgsConstructor
public class TeamGofAdminController {
    private final TeamGofAdminService teamGofAdminService;

    @PostMapping("create")
    @Operation(description = "Create an admin account")
    public ResponseEntity<ResponseDto<Long>> createAdmin(@RequestBody @Valid TeamGofAdminLoginInDto teamGofAdminLoginInDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(teamGofAdminService.createAdmin(teamGofAdminLoginInDto)));
    }

    @DeleteMapping("delete/{idAdmin}")
    @Operation(description = "Create an admin account")
    public ResponseEntity<ResponseDto<Boolean>> deleteAdmin(@PathVariable Long idAdmin) throws BusinessException {
        return ResponseEntity.ok().body(new ResponseDto<>(teamGofAdminService.deleteAdmin(idAdmin)));
    }
}
