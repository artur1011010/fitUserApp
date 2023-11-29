package pl.artur.zaczek.fit.user.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.artur.zaczek.fit.user.app.jpa.repository.UserRepository;
import pl.artur.zaczek.fit.user.app.mapper.UserMapper;
import pl.artur.zaczek.fit.user.app.rest.error.BadRequestException;
import pl.artur.zaczek.fit.user.app.rest.model.UserDto;
import pl.artur.zaczek.fit.user.app.service.UserService;
import pl.artur.zaczek.fit.user.app.service.auth.JwtService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userToUserDto)
                .toList();
    }

    @Override
    public void postUser(final UserDto userDto) {
        userRepository.save(userMapper.userDtoToUser(userDto));
    }

    @Override
    public UserDto getUserById(final Long userId) {
        return userMapper.userToUserDto(userRepository.getReferenceById(userId));
    }

    @Override
    public UserDto getMe(final String token) {
        final String email = jwtService.getEmailFromAuthorization(token);
        log.info("email: " + email);
        return userRepository
                .findByEmail(email)
                .map(userMapper::userToUserDto)
                .orElseThrow(()-> new BadRequestException("User not found by provided email"));
    }
}
