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
@CrossOrigin(origins = "*")
public class OpinionController {

    private final TrainerService trainerService;

    @PostMapping("/{trainerId}")
    public ResponseEntity<Void> addNewOpinion(@RequestHeader(name = "Authorization") final String token, @PathVariable final long trainerId, @RequestBody final OpinionDto opinionDto) {
        trainerService.postOpinion(token, trainerId, opinionDto);
        return ResponseEntity.ok().build();
    }
}
