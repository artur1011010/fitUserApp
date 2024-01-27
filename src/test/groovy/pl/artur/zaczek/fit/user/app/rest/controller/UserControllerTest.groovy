package pl.artur.zaczek.fit.user.app.rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pl.artur.zaczek.fit.user.app.jpa.entity.Trainer
import pl.artur.zaczek.fit.user.app.jpa.entity.User
import pl.artur.zaczek.fit.user.app.jpa.repository.TrainerRepository;
import pl.artur.zaczek.fit.user.app.jpa.repository.UserRepository

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerITSpec {

    private static final String USER_BASE_URL = '/user'

    @Autowired
    UserRepository userRepository

    @Autowired
    TrainerRepository trainerRepository

    @Autowired
    private MockMvc mockMvc

    def startup() {
        userRepository.deleteAll()
        trainerRepository.deleteAll()
    }

    def "get-all users should return empty result"() {
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + '/all')
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        then:
        response.andExpect(status().isOk())
    }

    def "get-all users should return correct result"() {
        given:
        def user = User.builder().id(1L)
                .name("test")
                .email("test")
                .phoneNumber("123")
                .build();
        userRepository.save(user);
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + '/all')
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        then:
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value("test"))
                .andExpect(jsonPath("[0].email").value("test"))
                .andExpect(jsonPath("[0].phoneNumber").value("123"))
    }

    def "get user/me return correct response"() {
        given:
        final Trainer trainer1 = Trainer.builder()
                .experience(24)
                .description("test")
                .isProfileActive(true)
                .photoNo(2)
                .specializations("specjalizacja")
                .build()
        final User user1 = User.builder()
                .trainer(trainer1)
                .email("mail1@op.pl")
                .name("Trener")
                .phoneNumber("+48 500 200 222")
                .build()
        trainer1.setUser(user1)
        userRepository.save(user1)
        mockTokenResponse()
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + '/me')
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON))

        then:
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$.name").value("Trener"))
                .andExpect(jsonPath("\$.email").value("mail1@op.pl"))
                .andExpect(jsonPath("\$.trainerDto.specializations").value("specjalizacja"))
                .andExpect(jsonPath("\$.trainerDto.experience").value("24"))
    }
}