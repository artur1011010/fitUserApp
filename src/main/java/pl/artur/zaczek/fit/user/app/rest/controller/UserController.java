package pl.artur.zaczek.fit.user.app.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.artur.zaczek.fit.user.app.rest.model.UserResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public ResponseEntity<UserResponse> getUser(){
        return ResponseEntity.ok(UserResponse
                .builder()
                .email("artur@ing.pl")
                .name("Artur")
                .build());
    }
}
