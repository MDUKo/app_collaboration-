package collaboration.groupe.collaboration.services.collabServices;

import collaboration.groupe.collaboration.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ProjetService {

 public Projet createProjetWithmember(Long idProjet,String email);

 public Projet findByIdProjet (Long id);

 public Projet updateProjet(Projet projet, Long id);

 public void deleteProjet(Long id);
}
