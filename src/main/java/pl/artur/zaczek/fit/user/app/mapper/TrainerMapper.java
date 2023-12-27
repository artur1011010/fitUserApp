package pl.artur.zaczek.fit.user.app.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.artur.zaczek.fit.user.app.jpa.entity.Trainer;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;

@Component
@Slf4j
public class TrainerMapper {
    public Trainer trainerDtoToTrainer(final TrainerDto dto) {
        return Trainer.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .experience(dto.getExperience())
                .specializations(dto.getSpecializations())
                .isProfileActive(dto.isProfileActive())
                .build();
    }

    public TrainerDto trainerToTrainerDto(final Trainer entity) {
        return TrainerDto.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .experience(entity.getExperience())
                .specializations(entity.getSpecializations())
                .isProfileActive(entity.getIsProfileActive())
                .userName(entity.getUser().getName())
                .build();
    }
}
