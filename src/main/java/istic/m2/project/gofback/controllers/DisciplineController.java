package istic.m2.project.gofback.controllers;

import istic.m2.project.gofback.controllers.dto.DisciplineInDto;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.services.DisciplineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/v1/disciplines")
@RequiredArgsConstructor
public class DisciplineController {

    private final DisciplineService disciplineService;

    @PostMapping(path = "add")
    public ResponseEntity<ResponseDto<ArrayList<Long>>> addDiscipline(@RequestBody @Valid DisciplineInDto disciplineInDto) {
        return ResponseEntity.ok(new ResponseDto<>(new ArrayList<>(disciplineService.addDiscipline(disciplineInDto))));
    }
}
