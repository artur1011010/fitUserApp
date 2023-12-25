package pl.artur.zaczek.fit.user.app.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.artur.zaczek.fit.user.app.rest.model.ClientDto;
import pl.artur.zaczek.fit.user.app.rest.model.RegisterUserRequest;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;
import pl.artur.zaczek.fit.user.app.rest.model.UserDto;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthenticationDto;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthenticationRequest;
import pl.artur.zaczek.fit.user.app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationDto> register(@Validated @RequestBody final RegisterUserRequest request) {
        log.info("POST /register:\n{}", request);
        final AuthenticationDto response = userService.registerNewUser(request);
        log.info("returning: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@Validated @RequestBody final AuthenticationRequest request) {
        log.info("POST /login:\n{}", request);
        final AuthenticationDto response = userService.loginUser(request);
        log.info("returning: {}", response);
        return ResponseEntity.ok(response);
    }

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

    @GetMapping("trainer")
    public ResponseEntity<TrainerDto> getMeTrainerData(@RequestHeader(name = "Authorization") final String token) {
        log.info("GET user/trainer, token: {}", token);
        final TrainerDto response = userService.getMeTrainer(token);
        log.info("returning: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("client")
    public ResponseEntity<ClientDto> getMeClientData(@RequestHeader(name = "Authorization") final String token) {
        log.info("GET user/client, token: {}", token);
        final ClientDto response = userService.getMeClient(token);
        log.info("returning: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/trainer")
    public ResponseEntity<Void> postTrainerProfile(@RequestBody final TrainerDto trainerDto, @RequestHeader(name = "Authorization") final String token) {
        log.info("POST user/trainer, token: {}\ntrainerDto: {}", token, trainerDto);
        userService.postTrainer(token, trainerDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/client")
    public ResponseEntity<Void> postClientProfile(@RequestBody final ClientDto clientDto, @RequestHeader(name = "Authorization") final String token) {
        log.info("POST user/client: clientDto: {}", clientDto);
        userService.postClient(token, clientDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> postUser(final UserDto userDto) {
        userService.postUser(userDto);
        return ResponseEntity.ok().build();
    }
}