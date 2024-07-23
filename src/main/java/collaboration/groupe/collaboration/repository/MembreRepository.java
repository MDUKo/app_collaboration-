package collaboration.groupe.collaboration.repository;

import collaboration.groupe.collaboration.entities.Membres;
import collaboration.groupe.collaboration.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembreRepository extends JpaRepository<Membres, Long> {

    public Iterable<Membres> findByEmail(String mail);

    public Membres findByIdMembres(Long id);



}
