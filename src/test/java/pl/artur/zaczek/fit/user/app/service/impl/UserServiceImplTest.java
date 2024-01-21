package pl.artur.zaczek.fit.user.app.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.artur.zaczek.fit.user.app.jpa.entity.User;
import pl.artur.zaczek.fit.user.app.jpa.repository.ClientRepository;
import pl.artur.zaczek.fit.user.app.jpa.repository.TrainerRepository;
import pl.artur.zaczek.fit.user.app.jpa.repository.UserRepository;
import pl.artur.zaczek.fit.user.app.mapper.ClientMapper;
import pl.artur.zaczek.fit.user.app.mapper.TrainerMapper;
import pl.artur.zaczek.fit.user.app.mapper.UserMapper;
import pl.artur.zaczek.fit.user.app.rest.error.BadRequestException;
import pl.artur.zaczek.fit.user.app.rest.model.RegisterUserRequest;
import pl.artur.zaczek.fit.user.app.rest.model.UserDto;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthenticationDto;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthenticationRequest;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthorizationDto;
import pl.artur.zaczek.fit.user.app.rest.model.auth.RegisterRequest;
import pl.artur.zaczek.fit.user.app.service.UserAuthClient;
import pl.artur.zaczek.fit.user.app.utilis.model.Gender;
import pl.artur.zaczek.fit.user.app.utilis.model.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    private final TrainerMapper trainerMapper = new TrainerMapper();
    private final UserMapper userMapper = new UserMapper(){
        @Override
        public UserDto userToUserDto(User userEntity) {
            return new UserDto();
        }
    };
    private final ClientMapper clientMapper = new ClientMapper();

    @Mock
    private UserRepository userRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private UserAuthClient authClient;

    private UserServiceImpl userService;

    private final String token = "dummyToken";
    private final String email = "user@example.com";
    private final User user = new User();
    private final AuthorizationDto authorizationDto = new AuthorizationDto(email, Role.USER);

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userMapper, trainerMapper, clientMapper, userRepository, trainerRepository, clientRepository, authClient);
        user.setEmail(email);
        when(authClient.authorize(token)).thenReturn(authorizationDto);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    }

    @Test
    @DisplayName("registerNewUser - success")
    void registerNewUserSuccess() {
        final RegisterUserRequest request = new RegisterUserRequest("John Doe", "john.doe@example.com", "123456", "PHONE_NUMBER", Gender.M, LocalDate.now());
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(authClient.register(any())).thenReturn(new AuthenticationDto("token", "token"));

        final AuthenticationDto result = userService.registerNewUser(request);

        assertNotNull(result);
        assertEquals("token", result.getAccessToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("registerNewUser - user already exists")
    void registerNewUserUserAlreadyExists() {
        final RegisterUserRequest request = new RegisterUserRequest("John Doe", "john.doe@example.com", "123456", "PHONE_NUMBER", Gender.M, LocalDate.now());
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));

        final Exception exception = assertThrows(BadRequestException.class, () -> userService.registerNewUser(request));

        assertEquals("User already exist", exception.getMessage());
        verify(authClient, never()).register(any(RegisterRequest.class));
    }

    @Test
    @DisplayName("loginUser - success")
    void loginUserSuccess() {
        when(authClient.login(new AuthenticationRequest("email", "pass"))).thenReturn(AuthenticationDto.builder()
                .accessToken("token1")
                .refreshToken("token2")
                .build());
        final AuthenticationDto result = userService.loginUser(new AuthenticationRequest("email", "pass"));

        assertNotNull(result);
        assertEquals("token1", result.getAccessToken());
        assertEquals("token2", result.getRefreshToken());
    }

    @Test
    @DisplayName("getAllUsers - returns list")
    void getAllUsersReturnsList() {
        when(userRepository.findAll()).thenReturn(List.of(new User()));
        final List<UserDto> result = userService.getAllUsers();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}