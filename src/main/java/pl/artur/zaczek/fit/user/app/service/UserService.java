package pl.artur.zaczek.fit.user.app.service;

import pl.artur.zaczek.fit.user.app.rest.model.ClientDto;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;
import pl.artur.zaczek.fit.user.app.rest.model.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);

    UserDto getMe(String token);

    TrainerDto getMeTrainer(String token);

    ClientDto getMeClient(String token);

    void postTrainer(String token, TrainerDto trainerDto);

    void postUser(UserDto userDto);
}
