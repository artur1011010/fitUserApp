package pl.artur.zaczek.fit.user.app.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.artur.zaczek.fit.user.app.utilis.model.FitnessLevel;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(schema = "fit_user_db", name = "CLIENTS")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="TEXT", length = 8000)
    private String bio;
    private String goals;
    @Enumerated(EnumType.STRING)
    private FitnessLevel fitnessLevel;
    @OneToOne
    @NotNull
    @ToString.Exclude
    private User user;
}
