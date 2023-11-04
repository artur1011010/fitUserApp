package pl.artur.zaczek.fit.user.app.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {
    private String name;
    private String email;
    private String nick;
}
