package pl.artur.zaczek.fit.user.app.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.artur.zaczek.fit.user.app.jpa.entity.Trainer;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;

class TrainerMapperTest {

    private final TrainerMapper trainerMapper = new TrainerMapper();

    @Test
    public void shouldMapTrainerDtoToTrainer() {
        final TrainerDto dto = TrainerDto.builder()
                .id(1L)
                .photoNo(1)
                .email("test@test.com")
                .phoneNumber("505505333")
                .userName("userName")
                .specializations("spec")
                .experience(18)
                .description("desc test")
                .isProfileActive(true)
                .build();

        final Trainer trainer = trainerMapper.trainerDtoToTrainer(dto);
        Assertions.assertEquals(trainer.getId(), dto.getId());
        Assertions.assertEquals(trainer.getPhotoNo(), dto.getPhotoNo());
        Assertions.assertEquals(trainer.getExperience(), dto.getExperience());
        Assertions.assertEquals(trainer.getSpecializations(), dto.getSpecializations());
        Assertions.assertEquals(trainer.getDescription(), dto.getDescription());
        Assertions.assertEquals(trainer.getIsProfileActive(), dto.isProfileActive());
    }
}