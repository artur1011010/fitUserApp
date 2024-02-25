package pl.artur.zaczek.fit.user.app.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.artur.zaczek.fit.user.app.jpa.entity.Photo;
import pl.artur.zaczek.fit.user.app.jpa.entity.Opinion;
import pl.artur.zaczek.fit.user.app.jpa.entity.Trainer;
import pl.artur.zaczek.fit.user.app.jpa.entity.User;
import pl.artur.zaczek.fit.user.app.jpa.repository.PhotoRepository;
import pl.artur.zaczek.fit.user.app.jpa.repository.TrainerRepository;
import pl.artur.zaczek.fit.user.app.jpa.repository.UserRepository;
import pl.artur.zaczek.fit.user.app.mapper.TrainerMapper;
import pl.artur.zaczek.fit.user.app.rest.error.BadRequestException;
import pl.artur.zaczek.fit.user.app.rest.error.NotFoundException;
import pl.artur.zaczek.fit.user.app.rest.model.OpinionDto;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDetails;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;
import pl.artur.zaczek.fit.user.app.service.TrainerService;
import pl.artur.zaczek.fit.user.app.service.UserAuthClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final PhotoRepository fileRepository;
    private final TrainerMapper trainerMapper;
    private final UserAuthClient authClient;

    @Override
    public List<TrainerDto> getAllTrainers(final boolean active) {
        return trainerRepository.findByIsProfileActive(active)
                .stream()
                .map(trainerMapper::trainerToTrainerDto).toList();
    }

    @Override
    public TrainerDetails getTrainerDetailsById(final long id) {
        return trainerMapper.trainerToTrainerDetails(trainerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Trainer not found by provided id: %s", id))));
    }

    @Override
    public void postOpinion(final String token, final long trainerId, final OpinionDto opinionDto) {
        final String email = authClient.authorize(token).email();
        log.info("email: " + email);
        final Trainer trainer = trainerRepository
                .findById(trainerId).orElseThrow(() -> new BadRequestException(String.format("Trainer not found by provided id: %s", trainerId)));
        final User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new BadRequestException(String.format("User not found by provided email: %s", email)));
        final Opinion newOpinion = Opinion
                .builder()
                .userName(user.getName())
                .userEmail(email)
                .content(opinionDto.getContent())
                .rating(opinionDto.getRating())
                .addedDate(LocalDateTime.now())
                .build();
        trainer.getOpinions().add(newOpinion);
        trainerRepository.save(trainer);
    }

    @Override
    @Transactional
    public void uploadPhoto(final MultipartFile file, final String token) throws IOException {
        final String email = authClient.authorize(token).email();
        log.info("email: " + email);
        userRepository
                .findByEmail(email)
                .orElseThrow(() -> new BadRequestException(String.format("User not found by provided email: %s", email)));

        final Optional<Photo> ownerEmail = fileRepository.findByOwnerEmail(email);
        final Photo newPhoto;
        if (ownerEmail.isPresent()) {
            newPhoto = ownerEmail.get();
            newPhoto.setData(file.getBytes());
            newPhoto.setFileName(file.getName());
        } else {
            newPhoto = new Photo();
            newPhoto.setFileName(file.getName());
            newPhoto.setData(file.getBytes());
            newPhoto.setOwnerEmail(email);
        }
        fileRepository.save(newPhoto);
    }

    @Override
    @Transactional
    public Photo downloadFile(final String email) {
        log.info("email: " + email);
        userRepository
                .findByEmail(email)
                .orElseThrow(() -> new BadRequestException(String.format("User not found by provided email: %s", email)));

        return fileRepository.findByOwnerEmail(email)
                .orElseThrow(() -> new BadRequestException("Trainer has no photo"));
    }

    @Transactional
    public void addTrainers() {
        final Opinion opinion = Opinion.builder()
                .rating(2)
                .content("Drogi i słabo")
                .userName("USer 1")
                .build();
        final Opinion opinion2 = Opinion.builder()
                .rating(2.5)
                .content("Słaby trener")
                .userName("User 2")
                .build();
        final Opinion opinion3 = Opinion.builder()
                .rating(5)
                .userName("User 3")
                .build();
        final Opinion opinion4 = Opinion.builder()
                .rating(3.5)
                .userName("User 4")
                .build();
        final Opinion opinion5 = Opinion.builder()
                .rating(4)
                .userName("User 5")
                .build();
        final Opinion opinion6 = Opinion.builder()
                .rating(2)
                .build();
        final Opinion opinion7 = Opinion.builder()
                .rating(4.5)
                .content("Ekstra polecam!")
                .build();

        final Trainer trainer = Trainer.builder()
                .experience(24)
                .description("Jestem Trenerem Personalnym Terapeutycznym wad postawy Pracuje nad poprawą ruchomości i mobilności ciała.Prowadzę zajęcia usprawniające Stretching,rozluźnianie zmęczonych mięśni,Zdrowy Kręgosłup,Pilates")
                .isProfileActive(true)
                .photoNo(1)
                .opinions(List.of(opinion7))
                .specializations("Trener sportowy, Rozciąganie, Gimnastyka, Pilates, Aerobik")
                .build();

        final Trainer trainer1 = Trainer.builder()
                .experience(24)
                .description("Jestem Trenerem Personalnym Terapeutycznym wad postawy Pracuje nad poprawą ruchomości i mobilności ciała.Prowadzę zajęcia usprawniające Stretching,rozluźnianie zmęczonych mięśni,Zdrowy Kręgosłup,Pilates")
                .isProfileActive(true)
                .photoNo(2)
                .opinions(List.of(opinion5, opinion6))
                .specializations("Kondycja fizyczna, Budowanie mięśni,Body combat\n")
                .build();

        final Trainer trainer2 = Trainer.builder()
                .experience(24)
                .description("Jestem Trenerem Personalnym Terapeutycznym wad postawy Pracuje nad poprawą ruchomości i mobilności ciała.Prowadzę zajęcia usprawniające Stretching,rozluźnianie zmęczonych mięśni,Zdrowy Kręgosłup")
                .isProfileActive(true)
                .photoNo(3)
                .opinions(List.of(opinion4))
                .specializations("Trener sportowy, Rozciąganie, Gimnastyka, Pilates, Aerobik")
                .build();

        final Trainer trainer3 = Trainer.builder()
                .experience(24)
                .description("Trener personalny oraz instruktor fitness Łódź. Prowadzenie online i stacjonarnie w Łodzi.")
                .isProfileActive(true)
                .photoNo(4)
                .opinions(List.of(opinion2, opinion3))
                .specializations("Trener sportowy, Budowanie mięśni, Kulturystyka, Cross training")
                .build();

        final Trainer trainer4 = Trainer.builder()
                .experience(24)
                .description("Jestem studentem 4 roku medycyny na kierunku lekarskim. Wiele lat na macie, siłowni i z nosem w mądrych książkach, dają mi wiedzę i doświadczenie, by poprowadzić cię sportową drogą.")
                .isProfileActive(true)
                .photoNo(5)
                .specializations("Fitness, Rozciąganie, Gimnastyka, Pilates, Aerobik")
                .build();

        final Trainer trainer5 = Trainer.builder()
                .experience(24)
                .description("Jestem trenerem personalnym, instruktorem fitness, koordynatorem strefy fitness oraz mentorem zdrowej zbilansowanej diety. Wieloletnie doświadczenie\n")
                .isProfileActive(true)
                .photoNo(6)
                .opinions(List.of(opinion))
                .specializations("Kondycja fizyczna, Fitness, Gimnastyka, Pilates,Budowanie mięśni, Aerobik, Pośladki, brzuch i uda")
                .build();

        final User user = User.builder()
                .trainer(trainer)
                .email("mail@op.pl")
                .name("Adam Trener")
                .phoneNumber("+48 500 200 222")
                .build();
        trainer.setUser(user);

        final User user1 = User.builder()
                .trainer(trainer1)
                .email("mail1@op.pl")
                .name("Krystian Trener")
                .phoneNumber("+48 500 200 222")
                .build();

        trainer1.setUser(user1);

        final User user2 = User.builder()
                .trainer(trainer2)
                .email("mail2@op.pl")
                .name("Iga Świątek")
                .phoneNumber("+48 500 200 222")
                .build();
        trainer2.setUser(user2);

        final User user3 = User.builder()
                .trainer(trainer3)
                .email("mail3@op.pl")
                .name("Mikołaj student")
                .phoneNumber("+48 500 200 222")
                .build();
        trainer3.setUser(user3);

        final User user4 = User.builder()
                .trainer(trainer4)
                .email("mail4@op.pl")
                .name("Aneta gimnastyka")
                .phoneNumber("+48 500 200 222")
                .build();
        trainer4.setUser(user4);

        final User user5 = User.builder()
                .trainer(trainer5)
                .email("mail5@op.pl")
                .name("Stanisław")
                .build();
        trainer5.setUser(user5);
        userRepository.saveAll(List.of(user, user1, user2, user3, user4, user5));
    }
}
