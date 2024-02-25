package pl.artur.zaczek.fit.user.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthenticationDto;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthenticationRequest;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthorizationDto;
import pl.artur.zaczek.fit.user.app.rest.model.auth.RegisterRequest;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserAuthClient {

    @Autowired
    Environment env;
    private final WebClient webClient;
    private final String url = getClientUrl();

    private String getClientUrl() {
        if (System.getenv("AUTH_URL") != null) {
            return System.getenv("AUTH_URL") + "/api/v1/auth";
        }
        return "http://localhost:1080/api/v1/auth";
    }

    public UserAuthClient(final WebClient.Builder webClientBuilder) {
        log.info("Creating userAuth client with url: {}", url);
        this.webClient = webClientBuilder.baseUrl(url).build();
    }

    public AuthenticationDto register(final RegisterRequest registerRequest) {
        return this.webClient.post().uri("/register")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(registerRequest), RegisterRequest.class)
                .retrieve()
                .bodyToMono(AuthenticationDto.class)
                .block();
    }

    public AuthenticationDto login(final AuthenticationRequest authenticationRequest) {
        return this.webClient.post().uri("/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .retrieve()
                .bodyToMono(AuthenticationDto.class)
                .block();
    }

    public AuthorizationDto authorize(final String token) {
        String AUTHORIZATION_HEADER = "Authorization";
        return this.webClient.get().uri("/authorize", token)
                .header(AUTHORIZATION_HEADER, token)
                .retrieve().bodyToMono(AuthorizationDto.class)
                .block();
    }
}
