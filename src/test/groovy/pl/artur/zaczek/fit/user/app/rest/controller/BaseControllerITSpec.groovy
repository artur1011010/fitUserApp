package pl.artur.zaczek.fit.user.app.rest.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.MediaType
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthorizationDto
import pl.artur.zaczek.fit.user.app.utilis.model.Role
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
abstract class BaseControllerITSpec extends Specification {

    @Shared
    static ClientAndServer mockServer;

    def setupSpec() {
        mockServer = ClientAndServer.startClientAndServer(8082);
    }

    def cleanupSpec() {
        mockServer.stop();
    }

    def mockTokenResponse() {
        def mapper = new ObjectMapper()
        mockServer.when(HttpRequest.request()
                .withMethod(HttpMethod.GET.name())
                .withHeader(Header.header("Authorization", "Bearer token"))
                .withPath('/api/v1/auth/authorize'))
                .respond(HttpResponse.response()
                        .withBody(mapper.writeValueAsString(new AuthorizationDto("mail1@op.pl", Role.USER)))
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withStatusCode(200))
    }
}