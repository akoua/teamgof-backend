package istic.m2.project.gofback.controllers;

import io.swagger.v3.oas.annotations.Operation;
import istic.m2.project.gofback.controllers.dto.PrecisionInDto;
import istic.m2.project.gofback.controllers.dto.PrecisionUpdateInDto;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.services.PrecisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/v1/precisions")
@RequiredArgsConstructor
public class PrecisionController {

    private final PrecisionService precisionService;

    @PostMapping(path = "create")
    @Operation(description = "add a new precision for a specific championship")
    public ResponseEntity<ResponseDto<ArrayList<Long>>> addPrecisionToEpreuve(@RequestBody @Valid PrecisionInDto precision) throws BusinessException {
        return ResponseEntity.ok(new ResponseDto<>(new ArrayList<>(precisionService.addPrecision(precision))));
    }

    @PutMapping(path = "update/{championshipId}")
    @Operation(description = "update a precision for a specific championship")
    public ResponseEntity<ResponseDto<Long>> addPrecisionToEpreuve(@PathVariable Long championshipId, @RequestBody @Valid PrecisionUpdateInDto precision) throws BusinessException {
        return ResponseEntity.ok(new ResponseDto<>(precisionService.updatePrecision(championshipId, precision)));
    }
}
