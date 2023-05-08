package istic.m2.project.gofback.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/api/test")
public class TestController {

    @GetMapping
    public ResponseEntity<String> getTest(){
        return ResponseEntity.ok("Test deploy");
    }
}
