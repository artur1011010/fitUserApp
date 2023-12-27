package pl.artur.zaczek.fit.user.app.service;

import pl.artur.zaczek.fit.user.app.rest.model.TrainerDto;

import java.util.List;

public interface TrainerService {
    List<TrainerDto> getAllTrainers(boolean active);

    void addTrainers();

}
