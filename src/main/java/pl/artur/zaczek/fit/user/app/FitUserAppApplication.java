package pl.artur.zaczek.fit.user.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.artur.zaczek.fit.user.app.rest.model.RegisterUserRequest;
import pl.artur.zaczek.fit.user.app.service.UserService;


@SpringBootApplication
public class FitUserAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitUserAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            UserService service
    ) {
        return args -> {
//            var admin = RegisterUserRequest.builder()
//                    .name("Admin")
//                    .email("admin@mail.com")
//                    .password("password")
//                    .build();
//            System.out.println("Admin token: " + service.registerNewUser(admin).getAccessToken());

//			var manager = RegisterUserRequest.builder()
//					.name("Manager")
//					.email("manager@mail.com")
//					.password("password")
//					.build();
//			System.out.println("Manager token: " + service.registerNewUser(manager).getAccessToken());
        };
    }
}