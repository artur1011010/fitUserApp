package pl.artur.zaczek.fit.user.app.service;

import pl.artur.zaczek.fit.user.app.rest.model.OpinionDto;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDetails;
import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;

import java.util.List;

public interface TrainerService {
    List<TrainerDto> getAllTrainers(boolean active);

    void addTrainers();

    TrainerDetails getTrainerDetailsById(long id);

    void postOpinion(String token, long id, OpinionDto opinionDto);
}
