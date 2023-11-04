package pl.artur.zaczek.fit.user.app.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "CLIENTS")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String bio;
    private String goals;
    @Enumerated(EnumType.STRING)
    private FitnessLevel fitnessLevel;
    @OneToOne
    private User user;
}
