package pl.artur.zaczek.fit.user.app.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.artur.zaczek.fit.user.app.jpa.entity.User;
import pl.artur.zaczek.fit.user.app.rest.model.ClientResponse;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerResponse;
import pl.artur.zaczek.fit.user.app.rest.model.UserDto;


@Component
@Slf4j
public class UserMapper {

    public UserDto userToUserDto(final User userEntity) {
        return UserDto.builder()
                .dateOfBirth(userEntity.getDateOfBirth())
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .phoneNumber(userEntity.getPhoneNumber())
                .avatar(userEntity.getAvatar())
                .clientResponse(userEntity.getClient() == null ? null : ClientResponse.builder()
                        .bio(userEntity.getClient().getBio())
                        .fitnessLevel(userEntity.getClient().getFitnessLevel())
                        .goals(userEntity.getClient().getGoals())
                        .id(userEntity.getClient().getId())
                        .build())
                .trainerResponse(userEntity.getTrainer() == null ? null : TrainerResponse.builder()
                        .id(userEntity.getTrainer().getId())
                        .experience(userEntity.getTrainer().getExperience())
                        .specializations(userEntity.getTrainer().getSpecializations())
                        .description(userEntity.getTrainer().getDescription())
                        .build())
                .build();
    }

    public User userDtoToUser(final UserDto userDto) {
        log.warn("UserMapper mapping UserDto -> User ignore Trainer and Client entity");
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .phoneNumber(userDto.getPhoneNumber())
                .avatar(userDto.getAvatar())
                .gender(userDto.getGender())
                .email(userDto.getEmail())
                .dateOfBirth(userDto.getDateOfBirth())
                .build();
    }

}
