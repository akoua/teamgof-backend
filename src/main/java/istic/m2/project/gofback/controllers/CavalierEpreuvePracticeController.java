package istic.m2.project.gofback.controllers;

import io.swagger.v3.oas.annotations.Operation;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.entities.CavalierEpreuvePractice;
import istic.m2.project.gofback.services.CavalierEpreuvePracticeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/api/CavalierEpreuvePractice")
@AllArgsConstructor
public class CavalierEpreuvePracticeController {

    private final CavalierEpreuvePracticeService service;

    @GetMapping("infos/{id}")
    @Operation(description = "Récupération de CavalierEpreuvePractice")
    public ResponseEntity<ResponseDto<CavalierEpreuvePractice>> getCavalierEpreuvePractice(@PathVariable("id") Long id) {
        CavalierEpreuvePractice cavalierEpreuvePractice = service.getCavalierEpreuvePractice(id);
        if (cavalierEpreuvePractice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ResponseDto<>(cavalierEpreuvePractice));
    }

    @PostMapping("add")
    @Operation(description = "Ajout de CavalierEpreuvePractice")
    public ResponseEntity<ResponseDto<CavalierEpreuvePractice>> addCreateCavalierEpreuvePractice(@RequestBody CavalierEpreuvePractice cavalierEpreuvePractice) {
        CavalierEpreuvePractice createdCavalierEpreuvePractice = service.addCreateCavalierEpreuvePractice(cavalierEpreuvePractice);
        return ResponseEntity.ok(new ResponseDto<>(cavalierEpreuvePractice));
    }

    @PutMapping("/{id}")
    @Operation(description = "Modification de CavalierEpreuvePractice")
    public ResponseEntity<ResponseDto<CavalierEpreuvePractice>> updateCavalierEpreuvePractice(@PathVariable("id") Long id, @RequestBody CavalierEpreuvePractice cavalierEpreuvePractice) {
        CavalierEpreuvePractice updatedCavalierEpreuvePractice = service.updateCavalierEpreuvePractice(id, cavalierEpreuvePractice);
        if (updatedCavalierEpreuvePractice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ResponseDto<>(updatedCavalierEpreuvePractice));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Suppression de CavalierEpreuvePractice")
    public ResponseEntity<Void> deleteCavalierEpreuvePractice(@PathVariable("id") Long id) {
        service.deleteCavalierEpreuvePractice(id);
        return ResponseEntity.noContent().build();
    }
}
