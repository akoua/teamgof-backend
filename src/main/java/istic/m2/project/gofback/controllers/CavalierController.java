package istic.m2.project.gofback.controllers;

import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.entities.Discipline;
import istic.m2.project.gofback.entities.Epreuve;
import istic.m2.project.gofback.repositories.CavalierRepository;
import istic.m2.project.gofback.repositories.DisciplineRepository;
import istic.m2.project.gofback.repositories.EpreuveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/api/cavalier")
@RequiredArgsConstructor
public class CavalierController {
    private final CavalierRepository cavalierRepository;
    @GetMapping("infos/{id}")
    public ResponseEntity<Cavalier> getCavalierInfos(@PathVariable Long id){
        var cavalier = cavalierRepository.getReferenceById(id);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)))
                .body(cavalier);
    }

    @PostMapping
    public ResponseEntity<String> addTest(){
        return ResponseEntity.ok("Test deploy New");
    }
}
