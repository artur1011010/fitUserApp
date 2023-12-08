package pl.artur.zaczek.fit.user.app.rest.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.artur.zaczek.fit.user.app.utilis.model.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterUserRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "(?<!\\w)(\\(?(\\+|00)?48\\)?)?[ -]?\\d{3}[ -]?\\d{3}[ -]?\\d{3}(?!\\w)")
    private String phoneNumber;
    @NotNull
    private Gender gender;
    @NotNull
    private LocalDate dateOfBirth;
}
