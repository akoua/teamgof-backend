package istic.m2.project.gofback.controllers;

import istic.m2.project.gofback.servicies.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping(path = "sign-up")
    public ResponseEntity<String> inscription(@RequestBody inscriptionInDto value) {
        String response = loginService.inscriptionService(value);
        return ResponseEntity.ok(response);
    }
}
