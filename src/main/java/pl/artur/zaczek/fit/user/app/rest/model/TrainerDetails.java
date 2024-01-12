package pl.artur.zaczek.fit.user.app.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TrainerDetails {
    private Long id;
    private String description;
    private int experience;
    private String specializations;
    private boolean isProfileActive;
    private String email;
    private String phoneNumber;
    private String userName;
    private int photoNo;
    private double rating;
    private List<OpinionDto> opinions;
}
