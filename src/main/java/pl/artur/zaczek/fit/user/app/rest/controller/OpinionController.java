package pl.artur.zaczek.fit.user.app.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.artur.zaczek.fit.user.app.rest.model.OpinionDto;
import pl.artur.zaczek.fit.user.app.service.TrainerService;

@RestController
@RequestMapping("/opinion")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class OpinionController {

    private final TrainerService trainerService;


    @PostMapping("/{id}")
    public ResponseEntity<Void> addNewOpinion (@RequestHeader(name = "Authorization") final String token, @PathVariable long id, @RequestBody OpinionDto opinionDto){
        trainerService.postOpinion(token, id, opinionDto);
        return ResponseEntity.ok().build();
    }
}
