package pl.artur.zaczek.fit.user.app.service;

import pl.artur.zaczek.fit.user.app.rest.model.ClientDto;
import pl.artur.zaczek.fit.user.app.rest.model.RegisterUserRequest;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;
import pl.artur.zaczek.fit.user.app.rest.model.UserDto;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthenticationDto;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthenticationRequest;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    AuthenticationDto registerNewUser(RegisterUserRequest registerUserRequest);

    AuthenticationDto loginUser(AuthenticationRequest AuthenticationRequest);

    UserDto getUserById(Long userId);

    UserDto getMe(String token);

    TrainerDto getMeTrainer(String token);

    ClientDto getMeClient(String token);

    void postTrainer(String token, TrainerDto trainerDto);

    void postClient(String token, ClientDto clientDto);

    void postUser(UserDto userDto);
}
