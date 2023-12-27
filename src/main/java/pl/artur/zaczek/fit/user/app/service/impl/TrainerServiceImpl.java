package pl.artur.zaczek.fit.user.app.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.artur.zaczek.fit.user.app.jpa.entity.Trainer;
import pl.artur.zaczek.fit.user.app.jpa.entity.User;
import pl.artur.zaczek.fit.user.app.jpa.repository.TrainerRepository;
import pl.artur.zaczek.fit.user.app.jpa.repository.UserRepository;
import pl.artur.zaczek.fit.user.app.mapper.TrainerMapper;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;
import pl.artur.zaczek.fit.user.app.service.TrainerService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final TrainerMapper trainerMapper;

    @Override
    public List<TrainerDto> getAllTrainers(boolean active) {
        return trainerRepository.findByIsProfileActive(active)
                .stream()
                .map(trainerMapper::trainerToTrainerDto).toList();
    }

    @Transactional
    public void addTrainers(){
        Trainer trainer = Trainer.builder()
                .experience(24)
                .description("Jestem Trenerem Personalnym Terapeutycznym wad postawy Pracuje nad poprawą ruchomości i mobilności ciała.Prowadzę zajęcia usprawniające Stretching,rozluźnianie zmęczonych mięśni,Zdrowy Kręgosłup,Pilat")
                .isProfileActive(true)
                .specializations("Trener sportowy, Rozciąganie, Gimnastyka, Pilates, Aerobik")
                .build();

        Trainer trainer1 = Trainer.builder()
                .experience(24)
                .description("Jestem Trenerem Personalnym Terapeutycznym wad postawy Pracuje nad poprawą ruchomości i mobilności ciała.Prowadzę zajęcia usprawniające Stretching,rozluźnianie zmęczonych mięśni,Zdrowy Kręgosłup,Pilat")
                .isProfileActive(true)
                .specializations("Kondycja fizyczna, Budowanie mięśni,Body combat\n")
                .build();

        Trainer trainer2 = Trainer.builder()
                .experience(24)
                .description("Jestem Trenerem Personalnym Terapeutycznym wad postawy Pracuje nad poprawą ruchomości i mobilności ciała.Prowadzę zajęcia usprawniające Stretching,rozluźnianie zmęczonych mięśni,Zdrowy Kręgosłup,Pilat")
                .isProfileActive(true)
                .specializations("Trener sportowy, Rozciąganie, Gimnastyka, Pilates, Aerobik")
                .build();

        Trainer trainer3 = Trainer.builder()
                .experience(24)
                .description("Trener personalny oraz instruktor fitness Łódź. Prowadzenie online i stacjonarnie w Łodzi.")
                .isProfileActive(true)
                .specializations("Trener sportowy, Budowanie mięśni, Kulturystyka, Cross training")
                .build();

        Trainer trainer4 = Trainer.builder()
                .experience(24)
                .description("Jestem studentem 4 roku medycyny na kierunku lekarskim. Wiele lat na macie, siłowni i z nosem w mądrych książkach, dają mi wiedzę i doświadczenie, by poprowadzić cię sportową drogą.")
                .isProfileActive(true)
                .specializations("Fitness, Rozciąganie, Gimnastyka, Pilates, Aerobik")
                .build();

        Trainer trainer5 = Trainer.builder()
                .experience(24)
                .description("Jestem trenerem personalnym, instruktorem fitness, koordynatorem strefy fitness oraz mentorem zdrowej zbilansowanej diety. Wieloletnie doświadczenie\n")
                .isProfileActive(true)
                .specializations("Kondycja fizyczna, Fitness, Gimnastyka, Pilates,Budowanie mięśni, Aerobik, Pośladki, brzuch i uda")
                .build();

        User user = User.builder()
                .trainer(trainer)
                .email("mail@op.pl")
                .name("Adam Trener")
                .build();
        trainer.setUser(user);

        User user1 = User.builder()
                .trainer(trainer1)
                .email("mail1@op.pl")
                .name("Krystian Trener")
                .build();

        trainer1.setUser(user1);

        User user2 = User.builder()
                .trainer(trainer2)
                .email("mail2@op.pl")
                .name("Iga Świątek")
                .build();
        trainer2.setUser(user2);

        User user3 = User.builder()
                .trainer(trainer3)
                .email("mail3@op.pl")
                .name("Mikołaj student")
                .build();
        trainer3.setUser(user3);

        User user4 = User.builder()
                .trainer(trainer4)
                .email("mail4@op.pl")
                .name("Aneta gimnastyka")
                .build();
        trainer4.setUser(user4);

        User user5 = User.builder()
                .trainer(trainer5)
                .email("mail5@op.pl")
                .name("Stanisław")
                .build();
        trainer5.setUser(user5);

        userRepository.saveAll(List.of(user, user1, user2, user3, user4, user5));
    }
}
