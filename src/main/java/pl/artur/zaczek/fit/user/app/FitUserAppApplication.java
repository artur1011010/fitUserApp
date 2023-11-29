package pl.artur.zaczek.fit.user.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pl.artur.zaczek.fit.user.app.rest.model.auth.RegisterRequest;
import pl.artur.zaczek.fit.user.app.service.auth.AuthenticationService;

import static pl.artur.zaczek.fit.user.app.utilis.model.Role.ADMIN;
import static pl.artur.zaczek.fit.user.app.utilis.model.Role.MANAGER;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class FitUserAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitUserAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .name("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.name("Manager")
					.email("manager@mail.com")
					.password("password")
					.role(MANAGER)
					.build();
			System.out.println("Manager token: " + service.register(manager).getAccessToken());
        };
    }
}