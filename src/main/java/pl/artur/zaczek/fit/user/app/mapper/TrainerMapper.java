package pl.artur.zaczek.fit.user.app.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.artur.zaczek.fit.user.app.jpa.entity.Opinion;
import pl.artur.zaczek.fit.user.app.jpa.entity.Trainer;
import pl.artur.zaczek.fit.user.app.rest.model.OpinionDto;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDetails;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;

import java.util.List;

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
                .photoNo(dto.getPhotoNo())
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
                .phoneNumber(entity.getUser().getPhoneNumber())
                .email(entity.getUser().getEmail())
                .photoNo(entity.getPhotoNo())
                .rating(entity.getOpinions().stream().mapToDouble(Opinion::getRating).average().orElse(0.0))
                .build();
    }

    private List<OpinionDto> mapOpionsToOpinionDtoList(final List<Opinion> opinions){
        return opinions.stream().map(opinion-> OpinionDto
                .builder()
                .content(opinion.getContent())
                .rating(opinion.getRating())
                .addedDate(opinion.getAddedDate())
                .userName(opinion.getUserName())
                .userEmail(opinion.getUserEmail())
                .id(opinion.getId())
                .build()).toList();
    }

    public TrainerDetails trainerToTrainerDetails(final Trainer entity) {
        return TrainerDetails.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .experience(entity.getExperience())
                .specializations(entity.getSpecializations())
                .isProfileActive(entity.getIsProfileActive())
                .userName(entity.getUser().getName())
                .phoneNumber(entity.getUser().getPhoneNumber())
                .email(entity.getUser().getEmail())
                .photoNo(entity.getPhotoNo())
                .rating(entity.getOpinions().stream().mapToDouble(Opinion::getRating).average().orElse(0.0))
                .opinions(mapOpionsToOpinionDtoList(entity.getOpinions()))
                .build();
    }
}
