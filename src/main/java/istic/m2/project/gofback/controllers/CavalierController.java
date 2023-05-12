package istic.m2.project.gofback.controllers;

import istic.m2.project.gofback.controllers.dto.CavalierOwnInfosDtoOut;
import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.services.CavalierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
