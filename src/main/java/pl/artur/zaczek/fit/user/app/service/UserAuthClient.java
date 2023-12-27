package pl.artur.zaczek.fit.user.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final WebClient webClient;

//    @Value("${application.security.url}")
    private final String authUrl = "http://localhost:8082/api/v1/auth";
    private final String AUTHORIZATION_HEADER = "Authorization";


    public UserAuthClient(final WebClient.Builder webClientBuilder) {
        log.info("Creating userAuth client with url: {}" , authUrl);
        this.webClient = webClientBuilder.baseUrl(authUrl).build();
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
                .bodyToMono(AuthenticationDto.class).block();
    }

    public AuthorizationDto authorize(final String token) {
        return this.webClient.get().uri("/authorize", token)
                .header(AUTHORIZATION_HEADER, token)
                .retrieve().bodyToMono(AuthorizationDto.class)
                .block();
    }
}