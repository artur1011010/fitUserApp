package pl.artur.zaczek.fit.user.app.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.artur.zaczek.fit.user.app.jpa.entity.Client;
import pl.artur.zaczek.fit.user.app.rest.model.ClientDto;

@Component
@Slf4j
public class ClientMapper {

    public Client clientDtoToClient(final ClientDto dto) {
        return Client.builder()
                .id(dto.getId())
                .bio(dto.getBio())
                .fitnessLevel(dto.getFitnessLevel())
                .goals(dto.getGoals())
                .build();
    }

    public ClientDto clientToClientDto(final Client entity) {
        return ClientDto.builder()
                .id(entity.getId())
                .bio(entity.getBio())
                .fitnessLevel(entity.getFitnessLevel())
                .goals(entity.getGoals())
                .build();
    }
}
