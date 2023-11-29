package pl.artur.zaczek.fit.user.app.rest.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.artur.zaczek.fit.user.app.utilis.model.Gender;
import pl.artur.zaczek.fit.user.app.utilis.model.Role;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @NotBlank
  private String name;
  @NotNull
  @Email
  private String email;
  @NotBlank
  private String password;
  @NotBlank
  @Pattern(regexp = "(?<!\\w)(\\(?(\\+|00)?48\\)?)?[ -]?\\d{3}[ -]?\\d{3}[ -]?\\d{3}(?!\\w)")
  private String phoneNumber;
  @NotNull
  private Gender gender;
  private LocalDate dateOfBirth;
  private Role role;
}
