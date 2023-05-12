package istic.m2.project.gofback.controllers;

import istic.m2.project.gofback.controllers.dto.CavalierOwnInfosDtoOut;
import istic.m2.project.gofback.controllers.dto.CavalierUpdateInDto;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.services.CavalierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping(path = "/v1/api/cavalier")
@RequiredArgsConstructor
public class CavalierController {
    private final CavalierService cavalierService;

    @GetMapping("infos/{id}")
    public ResponseEntity<ResponseDto<CavalierOwnInfosDtoOut>> getCavalierInfos(@PathVariable Long id) throws BusinessException {

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)))
                .body(new ResponseDto<>(cavalierService.findUserById(id)));
    }

    @PostMapping("update/infos")
    public ResponseEntity<ResponseDto<CavalierOwnInfosDtoOut>> updateCavalierInfos(@RequestBody @Valid CavalierUpdateInDto requestUpdate) throws BusinessException, IllegalAccessException {

        return ResponseEntity.ok()
                .body(new ResponseDto<>(cavalierService.updateInfosCavalier(requestUpdate)));
    }

}
