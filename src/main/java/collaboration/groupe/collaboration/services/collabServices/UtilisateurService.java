package collaboration.groupe.collaboration.services.collabServices;

import collaboration.groupe.collaboration.entities.Utilisateur;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UtilisateurService extends UserDetailsService {

    public Utilisateur createUser(Utilisateur utilisateur);

    public Utilisateur findUser(Long id);

    public Utilisateur updateUser(Utilisateur utilisateur, Long id);

    public void deleteUser(Long id);

//    public   Utilisateur  saveComptes( String email, String motDePasse);

}
