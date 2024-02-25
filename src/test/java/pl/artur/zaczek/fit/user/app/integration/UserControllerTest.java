package pl.artur.zaczek.fit.user.app.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.artur.zaczek.fit.user.app.FitUserAppApplication;
import pl.artur.zaczek.fit.user.app.jpa.entity.Trainer;
import pl.artur.zaczek.fit.user.app.jpa.entity.User;
import pl.artur.zaczek.fit.user.app.jpa.repository.UserRepository;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthorizationDto;
import pl.artur.zaczek.fit.user.app.utilis.model.Gender;
import pl.artur.zaczek.fit.user.app.utilis.model.Role;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FitUserAppApplication.class)
@ActiveProfiles(profiles = "test")
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;
    private static ClientAndServer mockServer;

    private MockMvc mockMvc;
    private static String BASE_URL = "/user";


    @BeforeAll
    static void startServer() {
        mockServer = startClientAndServer(1080);
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        userRepository.deleteAll();
    }


    @Test
    public void shouldReturnEmptyArrayResponseFromAll() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty())
                .andReturn();
    }

    @Test
    public void shouldReturnNotEmptyArrayFromAll () throws Exception {
        //given
        final User user = User.builder().id(1L)
                .name("test")
                .email("test")
                .phoneNumber("123")
                .gender(Gender.M)
                .build();
        userRepository.save(user);
        //when
        this.mockMvc.perform(get(BASE_URL + "/all"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].name").value("test"))
                .andExpect(jsonPath("$.[0].email").value("test"))
                .andExpect(jsonPath("$.[0].phoneNumber").value("123"))
                .andExpect(jsonPath("$.[0].gender").value("M"))
                .andExpect(jsonPath("$.[0].clientDto").isEmpty())
                .andExpect(jsonPath("$.[0].trainerDto").isEmpty())
                .andReturn();
    }

    @Test
    public void shouldReturnUserDtoFromUserMe() throws Exception {
        //given
        final Trainer trainer1 = Trainer.builder()
                .experience(24)
                .description("test")
                .isProfileActive(true)
                .photoNo(2)
                .specializations("specjalizacja")
                .build();
        final User user1 = User.builder()
                .trainer(trainer1)
                .email("mail1@op.pl")
                .name("Trener")
                .phoneNumber("+48 500 200 222")
                .build();
        trainer1.setUser(user1);
        userRepository.save(user1);
        mockTokenResponse();
        //when:
        final ResultActions response = mockMvc.perform(get(BASE_URL + "/me")
                .header("Authorization", "Bearer token")
                .contentType("APPLICATION_JSON"));
        //then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Trener"))
                .andExpect(jsonPath("$.email").value("mail1@op.pl"))
                .andExpect(jsonPath("$.trainerDto.specializations").value("specjalizacja"))
                .andExpect(jsonPath("$.trainerDto.experience").value("24"));
    }
    private void mockTokenResponse() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        mockServer.when(HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withHeader(Header.header("Authorization", "Bearer token"))
                        .withPath("/api/v1/auth/authorize"))
                .respond(HttpResponse.response()
                        .withBody(objectMapper.writeValueAsString(new AuthorizationDto("mail1@op.pl", Role.USER)))
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(200));
    }
}
