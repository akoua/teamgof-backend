package istic.m2.project.gofback.controllers;

import io.swagger.v3.oas.annotations.Operation;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.entities.enums.RoleType;
import istic.m2.project.gofback.services.TeamGofAdminService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/teamGofAdmin")
@RolesAllowed(value = {RoleType.RoleConstant.ADMIN})
//@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class TeamGofAdminController {
    private final TeamGofAdminService teamGofAdminService;

    @PostMapping("create")
    @Operation(description = "Create an admin account")
    public ResponseEntity<ResponseDto<Long>> createAdmin() {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(1L));
    }
}
