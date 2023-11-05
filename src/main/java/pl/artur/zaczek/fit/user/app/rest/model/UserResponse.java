package pl.artur.zaczek.fit.user.app.rest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.artur.zaczek.fit.user.app.jpa.entity.Client;
import pl.artur.zaczek.fit.user.app.jpa.entity.Gender;
import pl.artur.zaczek.fit.user.app.jpa.entity.Trainer;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private byte[] avatar;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Trainer trainer;
    private Client client;
}
