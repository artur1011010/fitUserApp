package pl.artur.zaczek.fit.user.app.rest.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OpinionDto {
    private Long id;
    private LocalDateTime addedDate;
    private String userName;
    private String userEmail;
    private double rating;
    private String content;
}
