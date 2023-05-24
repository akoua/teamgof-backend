package istic.m2.project.gofback.controllers;

import io.swagger.v3.oas.annotations.Operation;
import istic.m2.project.gofback.controllers.dto.DisciplineInDto;
import istic.m2.project.gofback.controllers.dto.DisciplineOutDto;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.services.DisciplineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/v1/disciplines")
@RequiredArgsConstructor
public class DisciplineController {

    private final DisciplineService disciplineService;

    @PostMapping(path = "add")
    public ResponseEntity<ResponseDto<ArrayList<Long>>> addDiscipline(@RequestBody @Valid DisciplineInDto disciplineInDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(new ArrayList<>(disciplineService.addDiscipline(disciplineInDto))));
    }

    @GetMapping(path = "all/infos")
    @Operation(description = "Get all discipline infos")
    public ResponseEntity<ResponseDto<ArrayList<DisciplineOutDto>>> getAllDisciplineInfos() {
        return ResponseEntity.ok(new ResponseDto<>(new ArrayList<>(disciplineService.getAllDisciplineInfos())));
    }
}
