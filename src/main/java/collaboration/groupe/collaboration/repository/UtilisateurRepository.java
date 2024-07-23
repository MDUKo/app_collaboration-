package collaboration.groupe.collaboration.repository;

import collaboration.groupe.collaboration.entities.Membres;
import collaboration.groupe.collaboration.entities.Projet;
import collaboration.groupe.collaboration.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    public Utilisateur findByEmailAndMotDePasse(String mail, String motDePasse);

    public Optional<Utilisateur> findByEmail(String mail);

    public Utilisateur findByNom(String nom);



}
