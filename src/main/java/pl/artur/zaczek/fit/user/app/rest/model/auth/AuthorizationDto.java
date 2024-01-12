package pl.artur.zaczek.fit.user.app.rest.model.auth;

import pl.artur.zaczek.fit.user.app.utilis.model.Role;

public record AuthorizationDto(String email, Role role) {
}
