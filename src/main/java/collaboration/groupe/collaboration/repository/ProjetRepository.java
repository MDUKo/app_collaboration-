package collaboration.groupe.collaboration.repository;

import collaboration.groupe.collaboration.entities.Membres;
import collaboration.groupe.collaboration.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

    public Projet findByIdProjet(Long id);
}
