package pl.artur.zaczek.fit.user.app.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.artur.zaczek.fit.user.app.jpa.entity.Client;
import pl.artur.zaczek.fit.user.app.rest.model.ClientDto;
import pl.artur.zaczek.fit.user.app.utilis.model.FitnessLevel;

class ClientMapperTest {
    private final ClientMapper clientMapper = new ClientMapper();

    @Test
    public void shouldMapClientDtoToClient() {
        final ClientDto dto = ClientDto.builder()
                .id(1L)
                .bio("Test Bio")
                .goals("Test Goals")
                .fitnessLevel(FitnessLevel.ELITE)
                .build();

        final Client client = clientMapper.clientDtoToClient(dto);
        Assertions.assertEquals(client.getId(), dto.getId());
        Assertions.assertEquals(client.getBio(), dto.getBio());
        Assertions.assertEquals(client.getGoals(), dto.getGoals());
        Assertions.assertEquals(client.getFitnessLevel(), dto.getFitnessLevel());
    }
}