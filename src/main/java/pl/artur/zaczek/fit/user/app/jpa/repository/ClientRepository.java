package pl.artur.zaczek.fit.user.app.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.artur.zaczek.fit.user.app.jpa.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository <Client, Long> {
}
