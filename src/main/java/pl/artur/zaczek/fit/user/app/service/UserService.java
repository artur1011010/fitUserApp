package pl.artur.zaczek.fit.user.app.service;

import pl.artur.zaczek.fit.user.app.rest.model.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();
}
