package pl.artur.zaczek.fit.user.app.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "FILES")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String ownerEmail;
    private String fileName;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "photo")
    private byte[] data;

}
