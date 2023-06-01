package istic.m2.project.gofback.controllers;

import istic.m2.project.gofback.controllers.dto.*;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.services.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping(path = "sign-up")
    public ResponseEntity<ResponseDto<String>> inscription(@RequestBody @Valid InscriptionInDto value) throws BusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(loginService.inscriptionService(value)));
    }

    @PostMapping(path = "sign-in")
    public ResponseEntity<ResponseDto<LoginOutDto>> connexion(@RequestBody @Valid LoginInDto loginInDto) throws BusinessException {
        return ResponseEntity.ok(new ResponseDto<>(loginService.connexionService(loginInDto)));
    }

    @PostMapping(path = "admin/sign-in")
    public ResponseEntity<ResponseDto<LoginAdminOutDto>> connexionAdmin(@RequestBody @Valid LoginInDto loginInDto) throws BusinessException {
        return ResponseEntity.ok(new ResponseDto<>(loginService.connexionAdminService(loginInDto)));
    }
}
