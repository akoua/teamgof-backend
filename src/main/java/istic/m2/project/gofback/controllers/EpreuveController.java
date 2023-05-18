package istic.m2.project.gofback.controllers;

import istic.m2.project.gofback.controllers.dto.EpreuveInDto;
import istic.m2.project.gofback.controllers.dto.EpreuveOutDto;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.services.EpreuveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/v1/epreuves")
@RequiredArgsConstructor
public class EpreuveController {

    private final EpreuveService epreuveService;

    @PostMapping(path = "add")
    public ResponseEntity<ResponseDto<ArrayList<EpreuveOutDto>>> addEpreuve(@RequestBody @Valid EpreuveInDto epreuve) throws BusinessException {
        return ResponseEntity.ok(new ResponseDto<>(new ArrayList<>(epreuveService.addEpreuve(epreuve))));
    }
}
