package pl.artur.zaczek.fit.user.app.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.artur.zaczek.fit.user.app.rest.model.ClientDto;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;
import pl.artur.zaczek.fit.user.app.rest.model.UserDto;
import pl.artur.zaczek.fit.user.app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserById(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe(@RequestHeader(name = "Authorization") final String token) {
        log.info("GET user/me, token: {}", token);
        return ResponseEntity.ok(userService.getMe(token));
    }

    @GetMapping("me/trainer")
    public ResponseEntity<TrainerDto> getMeTrainerData(@RequestHeader(name = "Authorization") final String token) {
        log.info("GET user/me/trainer, token: {}", token);
        final TrainerDto response = userService.getMeTrainer(token);
        log.info("returning: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("me/client")
    public ResponseEntity<ClientDto> getMeClientData(@RequestHeader(name = "Authorization") final String token) {
        log.info("GET user/me/client, token: {}", token);
        final ClientDto response = userService.getMeClient(token);
        log.info("returning: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/trainer")
    public ResponseEntity<Void> postTrainerProfile(@RequestHeader(name = "Authorization") final String token, @RequestBody final TrainerDto trainerDto) {
        log.info("GET user/me/client, token: {}\ntrainerDto: {}", token, trainerDto);
        userService.postTrainer(token, trainerDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> postUser(final UserDto userDto) {
        userService.postUser(userDto);
        return ResponseEntity.ok().build();
    }
}