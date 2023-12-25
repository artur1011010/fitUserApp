package pl.artur.zaczek.fit.user.app.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "TRAINERS")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private int experience;
    private String specializations;
    private Boolean isProfileActive;
    @OneToOne
    @NotNull
    @ToString.Exclude
    private User user;
}
