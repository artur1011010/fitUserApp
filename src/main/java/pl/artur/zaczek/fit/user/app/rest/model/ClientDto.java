package pl.artur.zaczek.fit.user.app.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.artur.zaczek.fit.user.app.utilis.model.FitnessLevel;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClientDto {
    private Long id;
    private String bio;
    private String goals;
    private FitnessLevel fitnessLevel;
}
