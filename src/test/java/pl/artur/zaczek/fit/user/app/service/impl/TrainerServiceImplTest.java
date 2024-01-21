package pl.artur.zaczek.fit.user.app.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import pl.artur.zaczek.fit.user.app.jpa.entity.File;
import pl.artur.zaczek.fit.user.app.jpa.entity.Opinion;
import pl.artur.zaczek.fit.user.app.jpa.entity.Trainer;
import pl.artur.zaczek.fit.user.app.jpa.entity.User;
import pl.artur.zaczek.fit.user.app.jpa.repository.FileRepository;
import pl.artur.zaczek.fit.user.app.jpa.repository.TrainerRepository;
import pl.artur.zaczek.fit.user.app.jpa.repository.UserRepository;
import pl.artur.zaczek.fit.user.app.mapper.TrainerMapper;
import pl.artur.zaczek.fit.user.app.rest.error.BadRequestException;
import pl.artur.zaczek.fit.user.app.rest.error.ForbiddenException;
import pl.artur.zaczek.fit.user.app.rest.error.NotFoundException;
import pl.artur.zaczek.fit.user.app.rest.model.OpinionDto;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDetails;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;
import pl.artur.zaczek.fit.user.app.rest.model.auth.AuthorizationDto;
import pl.artur.zaczek.fit.user.app.service.UserAuthClient;
import pl.artur.zaczek.fit.user.app.utilis.model.Role;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class TrainerServiceImplTest {

    private final TrainerMapper trainerMapper = new TrainerMapper();

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private UserAuthClient userAuthClient;

    private TrainerServiceImpl trainerService;


    private final MultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
    private final String token = "dummyToken";
    private final String email = "user@example.com";
    private final User user = new User();
    private final long trainerId = 1L;
    private final OpinionDto opinionDto = OpinionDto.builder().rating(5).content("Great session").build();

    @BeforeEach
    void setUp() {
        trainerService = new TrainerServiceImpl(trainerRepository, userRepository, fileRepository, trainerMapper, userAuthClient);
        user.setEmail(email);
        AuthorizationDto authorizationDto = new AuthorizationDto(email, Role.USER);
        when(userAuthClient.authorize(token)).thenReturn(authorizationDto);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    }

    @Test
    @DisplayName("Should return empty list when DB returns 0 results")
    public void shouldReturnEmptyListOfTrainers() {
        final boolean isTrainerActive = true;
        when(trainerRepository.findByIsProfileActive(isTrainerActive)).thenReturn(new ArrayList<>());
        List<TrainerDto> allTrainers = trainerService.getAllTrainers(isTrainerActive);
        assertNotNull(allTrainers);
        assertEquals(new ArrayList<>(), allTrainers);
    }

    @Test
    @DisplayName("Should return List of TrainerDtos and correct map Trainer entity")
    public void shouldReturnListOfTrainerDtos() {
        final boolean isTrainerActive = true;
        when(trainerRepository.findByIsProfileActive(isTrainerActive)).thenReturn(mockDbResults());
        final List<TrainerDto> allTrainers = trainerService.getAllTrainers(isTrainerActive);
        assertNotNull(allTrainers);
        assertEquals(2, allTrainers.size());
        final TrainerDto trainerDto1 = allTrainers.stream()
                .filter(trainer -> trainer.getId() == 1L)
                .findFirst().orElse(null);
        assertNotNull(trainerDto1);
        assertEquals(4.5, trainerDto1.getRating());
        assertEquals(24, trainerDto1.getExperience());
        assertEquals("Jestem Trenerem Personalnym Terapeutycznym wad postawy Pracuje nad poprawą ruchomości i mobilności ciała.", trainerDto1.getDescription());
        assertTrue(trainerDto1.isProfileActive());
        assertEquals("test@test.pl", trainerDto1.getEmail());
        assertEquals("userName1", trainerDto1.getUserName());


        final TrainerDto trainerDto2 = allTrainers.stream()
                .filter(trainer -> trainer.getId() == 2L)
                .findFirst().orElse(null);
        assertNotNull(trainerDto2);
        assertEquals(3, trainerDto2.getRating());
        assertEquals(10, trainerDto2.getExperience());
        assertEquals("Stretching,rozluźnianie zmęczonych mięśni,Zdrowy Kręgosłup,Pilates", trainerDto2.getDescription());
        assertTrue(trainerDto2.isProfileActive());
        assertEquals("test2@test.pl", trainerDto2.getEmail());
        assertEquals("userName2", trainerDto2.getUserName());

    }

    @Test
    @DisplayName("Should throw NotFountException when DB return no results on getTrainerDetailsById")
    public void shouldThrowNotFoundExceptionWhenDBReturnNoResultsOnGetTrainerDetailsById() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.empty());
        final NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> trainerService.getTrainerDetailsById(1));
        assertEquals("Trainer not found by provided id", notFoundException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), notFoundException.getCode());
    }

    @Test
    @DisplayName("Should return correct TrainerDetails")
    public void shouldReturnCorrectTrainerDetails() {
        when(trainerRepository.findById(1L)).thenReturn(mockDbResult());
        final TrainerDetails trainerDetailsById = trainerService.getTrainerDetailsById(1);
        assertNotNull(trainerDetailsById);
        assertEquals(4.5, trainerDetailsById.getRating());
        assertEquals(24, trainerDetailsById.getExperience());
        assertEquals("Jestem Trenerem Personalnym Terapeutycznym wad postawy Pracuje nad poprawą ruchomości i mobilności ciała.", trainerDetailsById.getDescription());
        assertTrue(trainerDetailsById.isProfileActive());
        assertEquals("test@test.pl", trainerDetailsById.getEmail());
        assertEquals("userName1", trainerDetailsById.getUserName());
        assertEquals(1, trainerDetailsById.getOpinions().size());
    }

    @Test
    @DisplayName("Should upload new photo when user exists and has no photo")
    void shouldUploadNewPhotoWhenUserExistsAndNoPhoto() throws IOException {
        when(fileRepository.findByOwnerEmail(email)).thenReturn(Optional.empty());
        trainerService.uploadPhoto(file, token);
        verify(fileRepository, times(1)).save(any(File.class));
    }

    @Test
    @DisplayName("Should update photo when user exists and already has a photo")
    void shouldUpdatePhotoWhenUserExistsAndHasPhoto() throws IOException {
        final File existingPhoto = new File();
        existingPhoto.setOwnerEmail(email);
        when(fileRepository.findByOwnerEmail(email)).thenReturn(Optional.of(existingPhoto));
        trainerService.uploadPhoto(file, token);
        verify(fileRepository, times(1)).save(existingPhoto);
    }

    @Test
    @DisplayName("Should throw BadRequestException when user does not exist")
    void shouldThrowBadRequestExceptionWhenUserDoesNotExist() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> trainerService.uploadPhoto(file, token));
        verify(fileRepository, never()).save(any(File.class));
    }

    @Test
    @DisplayName("Should throw UnauthorizedException when token is invalid")
    void shouldThrowUnauthorizedExceptionWhenTokenIsInvalid() {
        when(userAuthClient.authorize(token)).thenThrow(new ForbiddenException("Invalid token"));
        assertThrows(ForbiddenException.class, () -> trainerService.uploadPhoto(file, token));
        verify(fileRepository, never()).save(any(File.class));
        verify(userRepository, never()).findByEmail(email);
    }

    @Test
    @DisplayName("Should download file when user exists and has a photo")
    void shouldDownloadFileWhenUserExistsAndHasAPhoto() {
        final File expectedFile = new File();
        expectedFile.setOwnerEmail(email);
        when(fileRepository.findByOwnerEmail(email)).thenReturn(Optional.of(expectedFile));
        final File actualFile = trainerService.downloadFile(token);

        assertNotNull(actualFile);
        assertEquals(expectedFile, actualFile);
    }

    @Test
    @DisplayName("Should throw BadRequestException when user does not exist on download photo")
    void shouldThrowBadRequestExceptionWhenUserDoesNotExistWhenDownloadingPhoto() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> trainerService.downloadFile(token));
    }

    @Test
    @DisplayName("Should throw BadRequestException when user has no photo")
    void shouldThrowBadRequestExceptionWhenUserHasNoPhoto() {
        when(fileRepository.findByOwnerEmail(email)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> trainerService.downloadFile(token));
    }

    @Test
    @DisplayName("Should throw BadRequestException when trainer does not exist")
    void shouldThrowBadRequestExceptionWhenTrainerDoesNotExist() {
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(BadRequestException.class, () -> trainerService.postOpinion(token, trainerId, opinionDto));
        assertTrue(exception.getMessage().contains("Trainer not found by provided id: " + trainerId));
    }

    @Test
    @DisplayName("Should test add trainers")
    void shouldTestAddTreinersMethod() {
        assertDoesNotThrow(() -> trainerService.addTrainers());
        verify(userRepository, times(1)).saveAll(any());
    }


    private Optional<Trainer> mockDbResult(){
        final User user1 = User.builder().id(3L).email("test@test.pl").name("userName1").build();
        final Opinion opinion7 = Opinion.builder()
                .rating(4.5)
                .content("Ekstra polecam!")
                .build();
        final Trainer trainer = Trainer.builder()
                .id(1L)
                .user(user1)
                .experience(24)
                .description("Jestem Trenerem Personalnym Terapeutycznym wad postawy Pracuje nad poprawą ruchomości i mobilności ciała.")
                .isProfileActive(true)
                .photoNo(1)
                .opinions(List.of(opinion7))
                .specializations("Trener sportowy, Rozciąganie, Gimnastyka, Pilates, Aerobik")
                .build();
        return Optional.of(trainer);
    }


    private List<Trainer> mockDbResults(){
        final User user1 = User.builder().id(3L).email("test@test.pl").name("userName1").build();
        final User user2 = User.builder().id(4L).email("test2@test.pl").name("userName2").build();
        final Opinion opinion5 = Opinion.builder()
                .rating(4)
                .userName("Ada")
                .build();
        final Opinion opinion6 = Opinion.builder()
                .rating(2)
                .build();
        final Opinion opinion7 = Opinion.builder()
                .rating(4.5)
                .content("Ekstra polecam!")
                .build();

        final Trainer trainer = Trainer.builder()
                .id(1L)
                .user(user1)
                .experience(24)
                .description("Jestem Trenerem Personalnym Terapeutycznym wad postawy Pracuje nad poprawą ruchomości i mobilności ciała.")
                .isProfileActive(true)
                .photoNo(1)
                .opinions(List.of(opinion7))
                .specializations("Trener sportowy, Rozciąganie, Gimnastyka, Pilates, Aerobik")
                .build();

        final Trainer trainer1 = Trainer.builder()
                .id(2L)
                .user(user2)
                .experience(10)
                .description("Stretching,rozluźnianie zmęczonych mięśni,Zdrowy Kręgosłup,Pilates")
                .isProfileActive(true)
                .photoNo(2)
                .opinions(List.of(opinion5, opinion6))
                .specializations("Kondycja fizyczna, Budowanie mięśni,Body combat\n")
                .build();
        return List.of(trainer, trainer1);
    }
}