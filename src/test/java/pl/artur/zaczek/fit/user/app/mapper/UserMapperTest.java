package pl.artur.zaczek.fit.user.app.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.artur.zaczek.fit.user.app.jpa.entity.Trainer;
import pl.artur.zaczek.fit.user.app.jpa.entity.User;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;
import pl.artur.zaczek.fit.user.app.rest.model.UserDto;
import pl.artur.zaczek.fit.user.app.utilis.model.Gender;

import java.time.LocalDate;

public class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();


    @Test
    public void shouldMapUserToUserDto() {
        final UserDto dto = UserDto.builder()
                .id(1L)
                .email("test@test.com")
                .phoneNumber("505505333")
                .name("userName")
                .gender(Gender.M)
                .dateOfBirth(LocalDate.now())
                .build();

        final User user = userMapper.userDtoToUser(dto);
        Assertions.assertEquals(user.getId(), dto.getId());
        Assertions.assertEquals(user.getEmail(), dto.getEmail());
        Assertions.assertEquals(user.getPhoneNumber(), dto.getPhoneNumber());
        Assertions.assertEquals(user.getGender(), dto.getGender());
        Assertions.assertEquals(user.getDateOfBirth(), dto.getDateOfBirth());
    }
}
