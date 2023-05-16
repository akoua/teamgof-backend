package istic.m2.project.gofback.controllers;

import io.swagger.v3.oas.annotations.Operation;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.controllers.dto.RuleInDto;
import istic.m2.project.gofback.services.RuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/rules")
@RequiredArgsConstructor
public class RulesController {

    private final RuleService ruleService;

    @PostMapping(path = "add")
    @Operation(description = "add a new rules for a specific championship")
    public ResponseEntity<ResponseDto<String>> addRulesToEpreuve(@RequestBody @Valid RuleInDto rule) {
        return ResponseEntity.ok(new ResponseDto<>("vide"));
    }
}
