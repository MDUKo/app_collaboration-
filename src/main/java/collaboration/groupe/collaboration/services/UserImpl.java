package collaboration.groupe.collaboration.services;

import collaboration.groupe.collaboration.entities.*;
import collaboration.groupe.collaboration.enums.TypeRole;
import collaboration.groupe.collaboration.repository.MembreRepository;
import collaboration.groupe.collaboration.repository.UtilisateurRepository;
import collaboration.groupe.collaboration.services.collabServices.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
@AllArgsConstructor
@Service
public class UserImpl implements UtilisateurService{

    public static final String INVALID_USER = "Invalid User";
    public static final String USER_ALREADY_EXIST_HERE_PLEASE_CHANGE = "User already exist here please change";

   @Autowired
    public UserImpl(UtilisateurRepository utilisateurRepository, BCryptPasswordEncoder passwordEncoder,ValidationImpl validation,ProjetImpl projetService,MembreRepository membreRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationService=validation;
        this.projetService=projetService;
        this.membreRepository=membreRepository;
    }
    private MembreRepository membreRepository;
    private ProjetImpl projetService;
    private ValidationImpl validationService;

    private UtilisateurRepository utilisateurRepository;

    private BCryptPasswordEncoder passwordEncoder;
    private NotificationImpl notification;


//    public void register(Utilisateur utilisateur){
//
//        if(!utilisateur.getEmail().contains("@")){
//            throw new RuntimeException(INVALID_USER);
//        }
//        if(!utilisateur.getEmail().contains(".")){
//            throw new RuntimeException(INVALID_USER);
//        }
//        Optional<Utilisateur> utilisateurOptional= this.utilisateurRepository.findByEmail(utilisateur.getEmail());
//        if(utilisateurOptional.isPresent()){
//            throw new RuntimeException(USER_ALREADY_EXIST_HERE_PLEASE_CHANGE);
//        }
//        Membres membres= new Membres();
//        Roles rolesUser=new Roles();
//        rolesUser.setNomrole(TypeRole.USER);
//        String mdpCrypte= this.passwordEncoder.encode(utilisateur.getMotDePasse());
//        utilisateur.setMotDePasse(mdpCrypte);
//        utilisateur.setRoles(rolesUser);
//        //voyons si ca va fonctionner avec ses ajouts
//        membres.setUtilisateur(utilisateur);
//        utilisateur.getMembres().add(membres);
//        //*******                          ******//
//        utilisateur=this.utilisateurRepository.save(utilisateur);
//        this.membreRepository.save(membres);
//        this.validationService.saveValidation(utilisateur);
//        //Ajout du service notification
////        this.notification.sendMail(utilisateur);
//
//    }
public void register(Utilisateur utilisateur) {
    if (!utilisateur.getEmail().contains("@")) {
        throw new RuntimeException(INVALID_USER);
    }
    if (!utilisateur.getEmail().contains(".")) {
        throw new RuntimeException(INVALID_USER);
    }

    Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
    if (utilisateurOptional.isPresent()) {
        throw new RuntimeException(USER_ALREADY_EXIST_HERE_PLEASE_CHANGE);
    }

    Roles rolesUser = new Roles();
    rolesUser.setNomrole(TypeRole.USER);
    utilisateur.setRoles(rolesUser);

    String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMotDePasse());
    utilisateur.setMotDePasse(mdpCrypte);

    Membres membres = new Membres();
    membres.setNom(utilisateur.getNom());
    membres.setPrenom(utilisateur.getPrenom());
    membres.setEmail(utilisateur.getEmail());
    membres.setUtilisateur(utilisateur);
    utilisateur.getMembres().add(membres);
    membres.setUtilisateur(utilisateur);
    utilisateur.getMembres().add(membres);

    Utilisateur savedUtilisateur = this.utilisateurRepository.save(utilisateur);
    this.membreRepository.save(membres);

    this.validationService.saveValidation(savedUtilisateur);
    // Ajout du service notification
    // this.notification.sendMail(utilisateur);
}


    public void connection(Utilisateur utilisateur){
        this.utilisateurRepository.findByEmail(utilisateur.getEmail());

    }

    @Override
    // je vais modifié ici par userdetails
    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        return  this.utilisateurRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user exist with this  identified (email)"));
    }

    @Override
    public Utilisateur createUser(Utilisateur utilisateur) {
        return null;
    }

    @Override
    public Utilisateur findUser(Long id) {
        return this.utilisateurRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("No user has been seen")
        );
    }

    @Override
    public Utilisateur updateUser(Utilisateur utilisateur, Long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    public void activation(Map<String, String> activation) {
        Validation validation=this.validationService.readInFunctionOfCode(activation.get("code"));

        if(Instant.now().isAfter(validation.getExpiration())){
            throw new RuntimeException("Your mail has expired");
        }
       Utilisateur utilisateurActiver= this.utilisateurRepository.findById(validation.getUtilisateur().getIdUtilisateur()).orElseThrow(
                ()->new RuntimeException("User is not found")
        );
        utilisateurActiver.setActif(true);
        this.utilisateurRepository.save(utilisateurActiver);
    }


}
