package pl.artur.zaczek.fit.user.app.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

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
    @Column(columnDefinition="TEXT", length = 8000)
    private String description;
    private int experience;
    private String specializations;
    private Boolean isProfileActive;
    private int photoNo;
    @OneToOne
    @NotNull
    @ToString.Exclude
    private User user;
    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Opinion> opinions = new ArrayList<>();
}
