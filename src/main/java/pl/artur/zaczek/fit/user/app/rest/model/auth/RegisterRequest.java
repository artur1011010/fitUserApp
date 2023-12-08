package pl.artur.zaczek.fit.user.app.rest.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.artur.zaczek.fit.user.app.utilis.model.Role;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @NotNull
  @Email
  private String email;
  @NotBlank
  private String password;
  @NotNull
  private Role role;
}
