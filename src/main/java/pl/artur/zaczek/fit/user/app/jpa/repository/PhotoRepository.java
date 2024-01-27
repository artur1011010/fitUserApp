package pl.artur.zaczek.fit.user.app.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.artur.zaczek.fit.user.app.jpa.entity.Photo;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
        Optional<Photo> findByOwnerEmail(String ownerEmail);
}
