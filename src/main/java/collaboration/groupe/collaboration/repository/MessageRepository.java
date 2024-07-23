package collaboration.groupe.collaboration.repository;

import collaboration.groupe.collaboration.entities.Message;
import collaboration.groupe.collaboration.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderOrRecipient(Utilisateur user, Utilisateur user1);
}
