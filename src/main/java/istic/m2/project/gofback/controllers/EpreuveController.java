package istic.m2.project.gofback.controllers;

import io.swagger.v3.oas.annotations.Operation;
import istic.m2.project.gofback.controllers.dto.EpreuveInDto;
import istic.m2.project.gofback.controllers.dto.EpreuveOutDto;
import istic.m2.project.gofback.controllers.dto.EpreuveUpdateInDto;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.services.EpreuveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/v1/epreuves")
@RequiredArgsConstructor
public class EpreuveController {

    private final EpreuveService epreuveService;

    @GetMapping(path = "{id}")
    public ResponseEntity<ResponseDto<EpreuveOutDto>> getEpreuveInfos(@PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(new ResponseDto<>(epreuveService.getEpreuveInfos(id)));
    }

    @PostMapping(path = "add")
    public ResponseEntity<ResponseDto<ArrayList<EpreuveOutDto>>> addEpreuve(@RequestBody @Valid EpreuveInDto epreuve) throws BusinessException {
        return ResponseEntity.ok(new ResponseDto<>(new ArrayList<>(epreuveService.addEpreuve(epreuve))));
    }

    @PutMapping(path = "update")
    public ResponseEntity<ResponseDto<EpreuveOutDto>> updateEpreuve(@RequestBody @Valid EpreuveUpdateInDto epreuve) throws BusinessException {
        return ResponseEntity.ok(new ResponseDto<>(epreuveService.updateEpreuve(epreuve)));
    }

    @DeleteMapping("/delete/{idChampionship}")
    @Operation(description = "Delete championship and all information's about it")
    public ResponseEntity<ResponseDto<Boolean>> deleteEpreuve(@PathVariable Long idChampionship) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(epreuveService.deleteChampionship(idChampionship)));
    }
}
