package pl.artur.zaczek.fit.user.app.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RatingDto {
    private int rating;
    private boolean userVoted;
}
