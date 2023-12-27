package pl.artur.zaczek.fit.user.app.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.artur.zaczek.fit.user.app.jpa.entity.Client;
import pl.artur.zaczek.fit.user.app.jpa.entity.Trainer;
import pl.artur.zaczek.fit.user.app.jpa.entity.User;
import pl.artur.zaczek.fit.user.app.jpa.repository.ClientRepository;
import pl.artur.zaczek.fit.user.app.jpa.repository.TrainerRepository;
import pl.artur.zaczek.fit.user.app.jpa.repository.UserRepository;
import pl.artur.zaczek.fit.user.app.mapper.ClientMapper;
import pl.artur.zaczek.fit.user.app.mapper.TrainerMapper;
import pl.artur.zaczek.fit.user.app.mapper.UserMapper;
import pl.artur.zaczek.fit.user.app.rest.error.BadRequestException;
import pl.artur.zaczek.fit.user.app.rest.error.NotFoundException;
import pl.artur.zaczek.fit.user.app.rest.model.ClientDto;
import pl.artur.zaczek.fit.user.app.rest.model.RegisterUserRequest;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;
import pl.artur.zaczek.fit.user.app.rest.model.UserDto;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthenticationDto;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthenticationRequest;
import pl.artur.zaczek.fit.user.app.rest.model.auth.RegisterRequest;
import pl.artur.zaczek.fit.user.app.service.UserAuthClient;
import pl.artur.zaczek.fit.user.app.service.UserService;
import pl.artur.zaczek.fit.user.app.utilis.model.Role;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final TrainerMapper trainerMapper;
    private final ClientMapper clientMapper;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final ClientRepository clientRepository;
    private final UserAuthClient authClient;

    @Override
    @Transactional
    public AuthenticationDto registerNewUser(final RegisterUserRequest registerUserRequest) {
        Optional<User> byEmail = userRepository.findByEmail(registerUserRequest.getEmail());
        if (byEmail.isEmpty()) {
            final RegisterRequest request = RegisterRequest.builder()
                    .email(registerUserRequest.getEmail())
                    .role(Role.USER)
                    .password(registerUserRequest.getPassword())
                    .build();
            final AuthenticationDto authenticationDto = authClient.register(request);
            final User user = User.builder()
                    .name(registerUserRequest.getName())
                    .email(registerUserRequest.getEmail())
                    .phoneNumber(registerUserRequest.getPhoneNumber())
                    .gender(registerUserRequest.getGender())
                    .dateOfBirth(registerUserRequest.getDateOfBirth())
                    .build();
            log.info("user before save: {}", user);
            userRepository.save(user);
            return authenticationDto;
        }
        throw new BadRequestException("User already exist");
    }

    @Override
    public AuthenticationDto loginUser(final AuthenticationRequest request) {
        return authClient.login(request);
    }

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
        final String email = authClient.authorize(token).email();
        log.info("email: " + email);
        return userRepository
                .findByEmail(email)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new BadRequestException("User not found by provided email"));
    }

    @Override
    public TrainerDto getMeTrainer(final String token) {
        final String email = authClient.authorize(token).email();
        log.info("email: " + email);
        return userRepository
                .findByEmail(email)
                .map(userMapper::userToUserDto)
                .map(UserDto::getTrainerDto)
                .orElseThrow(() -> new NotFoundException("User does not have trainer profile"));
    }

    @Override
    public ClientDto getMeClient(final String token) {
        final String email = authClient.authorize(token).email();
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
        final String email = authClient.authorize(token).email();
        log.info("email: " + email);
        final Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            final User user = byEmail.get();
            if (user.getTrainer() == null) {
                final Trainer trainer = trainerMapper.trainerDtoToTrainer(trainerDto);
                trainer.setUser(user);
                user.setTrainer(trainer);
            } else {
                final Trainer trainer = user.getTrainer();
                trainer.setDescription(trainerDto.getDescription());
                trainer.setExperience(trainerDto.getExperience());
                trainer.setSpecializations(trainerDto.getSpecializations());
                trainer.setIsProfileActive(trainerDto.isProfileActive());
                trainerRepository.save(trainer);
            }
            userRepository.save(user);
        } else {
            log.error("nie znaleziono profilu po email: " + email);
        }
    }

    @Override
    @Transactional
    public void postClient(final String token, final ClientDto clientDto) {
        final String email = authClient.authorize(token).email();
        log.info("email: " + email);
        final Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            final User user = byEmail.get();
            if (user.getClient() == null) {
                final Client client = clientMapper.clientDtoToClient(clientDto);
                client.setUser(user);
                user.setClient(client);
            } else {
                final Client client = user.getClient();
                client.setBio(clientDto.getBio());
                client.setGoals(clientDto.getGoals());
                client.setFitnessLevel(clientDto.getFitnessLevel());
                clientRepository.save(client);
            }
            userRepository.save(user);
            log.info("User:\n" + user);
        } else {
            log.error("nie znaleziono profilu po email: " + email);
        }
    }
}