package pl.artur.zaczek.fit.user.app.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.artur.zaczek.fit.user.app.jpa.entity.Trainer;
import pl.artur.zaczek.fit.user.app.jpa.entity.User;
import pl.artur.zaczek.fit.user.app.jpa.repository.UserRepository;
import pl.artur.zaczek.fit.user.app.mapper.TrainerMapper;
import pl.artur.zaczek.fit.user.app.mapper.UserMapper;
import pl.artur.zaczek.fit.user.app.rest.error.BadRequestException;
import pl.artur.zaczek.fit.user.app.rest.error.NotFoundException;
import pl.artur.zaczek.fit.user.app.rest.model.ClientDto;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;
import pl.artur.zaczek.fit.user.app.rest.model.UserDto;
import pl.artur.zaczek.fit.user.app.service.UserService;
import pl.artur.zaczek.fit.user.app.service.auth.JwtService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final TrainerMapper trainerMapper;
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
                .orElseThrow(() -> new BadRequestException("User not found by provided email"));
    }

    @Override
    public TrainerDto getMeTrainer(final String token) {
        final String email = jwtService.getEmailFromAuthorization(token);
        log.info("email: " + email);
        return userRepository
                .findByEmail(email)
                .map(userMapper::userToUserDto)
                .map(UserDto::getTrainerDto)
                .orElseThrow(() -> new NotFoundException("User does not have trainer profile"));
    }

    @Override
    public ClientDto getMeClient(final String token) {
        final String email = jwtService.getEmailFromAuthorization(token);
        log.info("email: " + email);
        return userRepository
                .findByEmail(email)
                .map(userMapper::userToUserDto)
                .map(UserDto::getClientDto)
                .orElseThrow(() -> new NotFoundException("User does not have client profile"));
    }

    @Override
    @Transactional
    public void postTrainer(String token, TrainerDto trainerDto) {
        final String email = jwtService.getEmailFromAuthorization(token);
        final Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            final Trainer trainer = trainerMapper.userToUserDto(trainerDto);
            final User user = byEmail.get();
            user.setTrainer(trainer);
        } else {
            log.error("nie znaleziono profilu usera");
        }
    }
}