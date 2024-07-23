package collaboration.groupe.collaboration.services;

import collaboration.groupe.collaboration.entities.Membres;
import collaboration.groupe.collaboration.entities.Projet;
import collaboration.groupe.collaboration.entities.Utilisateur;
import collaboration.groupe.collaboration.repository.MembreRepository;
import collaboration.groupe.collaboration.repository.ProjetRepository;
import collaboration.groupe.collaboration.repository.UtilisateurRepository;
import collaboration.groupe.collaboration.services.collabServices.ProjetService;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.core.context.SecurityContextHolder.*;
@AllArgsConstructor
@Service
public class ProjetImpl implements ProjetService {


  
    private ProjetRepository projetRepository;
    
    private MembreRepository membreRepository;
   
    private UtilisateurRepository utilisateurRepository;

    


    @Override
    public Projet createProjetWithmember(Long idProjet, String email) {
           Iterable<Membres> listmembre=  membreRepository.findByEmail(email);
           Utilisateur utilisateur= (Utilisateur)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//           projetRepository.findById(idProjet);
           projetRepository.findByIdProjet(idProjet);
           Projet projetCreated= new Projet();
           projetCreated.setIdProjet(idProjet);
           projetCreated.setDate_creation(new Date());
           projetCreated.setDate_debut(projetCreated.getDate_debut());
           projetCreated.setDate_fin(projetCreated.getDate_fin());
           projetCreated.setUtilisateur(utilisateur);
           projetCreated.setDescription_projet(projetCreated.getDescription_projet());
           projetCreated.setTitre(projetCreated.getTitre());
           projetCreated.setMembres((List<Membres>) listmembre);
           return projetRepository.save(projetCreated);
    }

    public Projet createProjetWithmembers(Projet projet, String email) {
        Iterable<Membres> listmembre=  membreRepository.findByEmail(email);;
//           projetRepository.findById(idProjet);

        Projet projetCreated= new Projet();
        projetCreated.setIdProjet(projet.getIdProjet());
        projetCreated.setDate_creation(new Date());
        projetCreated.setDate_debut(projet.getDate_debut());
        projetCreated.setDate_fin(projet.getDate_fin());
        projetCreated.setDescription_projet(projet.getDescription_projet());
        projetCreated.setTitre(projet.getTitre());
        projetCreated.setMembres((List<Membres>) listmembre);
        return projetRepository.save(projetCreated);
    }

    //J'ai rencontré beaucoup de probème pour la creation de projet et là j'arrive toujours lorsque j'utilise le token fourni par l'utilisateur(la cause la conversion de d'un objet en String)
    //J'ai dont utilisé plusieurs approche et làs meme problème Mr
    public Projet createProjets(Projet projet) {
        Utilisateur utilisateurs=new Utilisateur();
            Utilisateur utilisateur= (Utilisateur) getContext().getAuthentication().getPrincipal();
            projet.setUtilisateur((Utilisateur) utilisateur);
//           projetsave.setTitre(projet.getTitre());
//           projetsave.setDescription_projet(projet.getDescription_projet());
//           projetsave.setDate_debut(projet.getDate_debut());
//           projetsave.setDate_fin(projet.getDate_fin());
//            projet.setUtilisateur(utilisateur);
           projet.setDate_creation(new Date());
//
          return projetRepository.save(projet);
    }

//    public Projet createProjets(Projet projet) {
//        // Récupérer l'utilisateur principal
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("Type de l'objet principal : " + principal.getClass().getName());
//        // Vérifier le type de l'objet principal
//        if (principal instanceof String) {
//            String username = (String) principal;
//            Utilisateur utilisateur = new Utilisateur();
//            this.utilisateurRepository.findByNom(utilisateur.getEmail());
//            projet.setUtilisateur(utilisateur);
//        } else {
//            // Si l'objet principal est déjà de type Utilisateur, vous pouvez le caster directement
//            Utilisateur utilisateur = (Utilisateur) principal;
//            projet.setUtilisateur(utilisateur);
//        }
//        projet.setDate_creation(new Date());
//        return projetRepository.save(projet);
//    }

//    public Projet createProjets(Projet projet) {
////         Récupérer l'utilisateur principal à partir de la base de données
//        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Utilisateur utilisateur= new Utilisateur(); utilisateurRepository.findByEmail(utilisateur.getEmail()).orElseThrow(
//                () -> new RuntimeException("Utilisateur non trouvé pour l'email : " + username));
//        projet.setUtilisateur(utilisateur);
//        projet.setDate_creation(new Date());
//        return projetRepository.save(projet);
    //}

//    public void createProjets(Projet projet) {
//        Object prinipal=null;
//        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        projet.setUtilisateur((Utilisateur) prinipal);
//
////        Utilisateur utilisateur= (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//              projet.setDate_creation(new Date());
//              projetRepository.save(projet);
//    }



    public void addMembreToProjet(Long idProjet, Long idMembre) {
           Projet projet = projetRepository.findById(idProjet).orElse(null);
           Membres membre = membreRepository.findById(idMembre).orElse(null);

           if (projet != null && membre != null) {
                projet.getMembres().add(membre);
                projetRepository.save(projet);
           }
    }

    @Override
    public Projet findByIdProjet(Long id) {

           Projet projet= projetRepository.findByIdProjet(id);;
           System.out.println("Le projet cherché  a le id:"+id);
          return projet;
    }


    public List<Projet> getAllProjets() {
           List<Projet> projetList=projetRepository.findAll();
           return projetList;
    }

    @Override
    public Projet updateProjet(Projet projet, Long id) {
           projetRepository.findByIdProjet(id);
           Projet projetUpdate= new Projet();
           projetUpdate.setIdProjet(id);
           projetUpdate.setTitre(projet.getTitre());
           projetUpdate.setDescription_projet(projet.getDescription_projet());
           projetUpdate.setDate_creation(new Date());
           projetUpdate.setDate_debut(projet.getDate_debut());
           projetUpdate.setDate_fin(projet.getDate_fin());
           projetUpdate.setMembres(projet.getMembres());
           projetRepository.save(projetUpdate);
           return projetUpdate;
    }

    @Override
    public void deleteProjet(Long id) {
           if(id!=null) {
            projetRepository.deleteById(id);
          }
    }
}
