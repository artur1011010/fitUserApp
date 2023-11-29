package pl.artur.zaczek.fit.user.app.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TrainerResponse {
    private Long id;
    private String description;
    private int experience;
    private String specializations;
}
