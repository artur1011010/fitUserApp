package pl.artur.zaczek.fit.user.app.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;
import pl.artur.zaczek.fit.user.app.service.TrainerService;

import java.util.List;

@RestController
@RequestMapping("/trainer")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class TrainerController {

    private final TrainerService trainerService;

    @GetMapping
    public ResponseEntity<List<TrainerDto>> getAllTrainers (@RequestParam boolean active){
        log.info("GET /trainer active:{}", active);
        return ResponseEntity.ok(trainerService.getAllTrainers(active));
    }

    @PostMapping("/add5")
    public ResponseEntity<Void> addTrainers(){
        trainerService.addTrainers();
        return ResponseEntity.ok().build();
    }
}
