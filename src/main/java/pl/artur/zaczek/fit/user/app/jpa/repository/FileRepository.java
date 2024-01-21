package pl.artur.zaczek.fit.user.app.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.artur.zaczek.fit.user.app.jpa.entity.File;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
        Optional<File> findByOwnerEmail(String ownerEmail);
}
