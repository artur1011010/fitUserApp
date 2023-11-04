package pl.artur.zaczek.fit.user.app.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    @Lob
    @Column(name = "avatar")
    private byte[] avatar;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate dateOfBirth;
    @OneToOne
    private Trainer trainer;
    @OneToOne
    private Client client;
}
