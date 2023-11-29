package pl.artur.zaczek.fit.user.app.service;

import pl.artur.zaczek.fit.user.app.rest.model.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);
    UserDto getMe(String token);

    void postUser(UserDto userDto);
}
