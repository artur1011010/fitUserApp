package pl.artur.zaczek.fit.user.app.mapper;

import org.mapstruct.Mapper;
import pl.artur.zaczek.fit.user.app.jpa.entity.User;
import pl.artur.zaczek.fit.user.app.rest.model.UserResponse;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface UserMapper {
    UserResponse userToUserResponse(User userEntity);

}
