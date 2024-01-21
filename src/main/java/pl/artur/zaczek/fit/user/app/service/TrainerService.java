package pl.artur.zaczek.fit.user.app.service;

import org.springframework.web.multipart.MultipartFile;
import pl.artur.zaczek.fit.user.app.jpa.entity.File;
import pl.artur.zaczek.fit.user.app.rest.model.OpinionDto;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDetails;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;

import java.io.IOException;
import java.util.List;

public interface TrainerService {
    List<TrainerDto> getAllTrainers(boolean active);

    void addTrainers();

    TrainerDetails getTrainerDetailsById(long id);

    void postOpinion(String token, long id, OpinionDto opinionDto);

    void uploadPhoto(MultipartFile file, String token) throws IOException;

    File downloadFile(String token);
}
