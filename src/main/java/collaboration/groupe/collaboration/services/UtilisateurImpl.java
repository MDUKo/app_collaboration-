//package collaboration.groupe.collaboration.services;
//
//import collaboration.groupe.collaboration.entities.Projet;
//import collaboration.groupe.collaboration.entities.Utilisateur;
//import collaboration.groupe.collaboration.repository.ProjetRepository;
//import collaboration.groupe.collaboration.repository.UtilisateurRepository;
//import collaboration.groupe.collaboration.security.PasswordEncoderImpl;
//import collaboration.groupe.collaboration.services.collabServices.UtilisateurService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import java.util.List;
//import java.util.Optional;
//
//
//@Service
//public class UtilisateurImpl implements UtilisateurService {
//
//
//    @Autowired
//    private UtilisateurRepository utilisateurRepository;
//
//    @Autowired
//    private ProjetRepository projetRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public UtilisateurImpl(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
//        this.utilisateurRepository = utilisateurRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Bean
//    public PasswordEncoder passwordEncoders(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new PasswordEncoderImpl();
//    }
//
//
////
////    @Autowired
////    private BCryptPasswordEncoder passwordEncoder;
////
////    private Map<String, Utilisateur> utilisateurMap=new HashMap<>();
//
//
////    private AuthenticationManager authenticationManager;
////
////    @Autowired
////    private UserDetailsService userDetailsService;
////
////
////    public void authenticate(String username, String password){
////        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
////                new UsernamePasswordAuthenticationToken(username,password);
////        Authentication authentication=authenticationManager.authenticate(usernamePasswordAuthenticationToken);
////        SecurityContextHolder.getContext().setAuthentication(authentication);
////    }
////
////    public UserDetails getUsersDetails(String username){
////        try{
////            return userDetailsService.loadUserByUsername(username);
////        }catch (UsernameNotFoundException e){
////            throw new UsernameNotFoundException("Utilisateur not found:"+username);
////        }
////    }
////
////    public void login(String username,String password){
////        authenticate(username, password);
////        UserDetails userDetails=getUsersDetails(username);
////    }
//
//
//
//    @Override
//    public Utilisateur createUser(Utilisateur utilisateur) {
//           Utilisateur utilisateurCreate = new Utilisateur();
//           utilisateurCreate.setEmail(utilisateur.getEmail());
//           utilisateurCreate.setMotDePasse(bCryptPasswordEncoder.encode(utilisateur.getMotDePasse()));
//           Utilisateur utilisateursave=utilisateurRepository.save(utilisateurCreate);
//           return utilisateursave;
//    }
//
//    @Override
//    public Utilisateur findUser(Long id) {
//          return utilisateurRepository.findById(id).orElse(null);
//    }
//
//    public Utilisateur assignProjetToUtilisateur(Long idUtilisateur, Long idProjet) {
//           Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur).orElse(null);
//           Projet projet = projetRepository.findById(idProjet).orElse(null);
//           if (utilisateur != null && projet != null) {
//              utilisateur.getProjet().add(projet);
//              utilisateurRepository.save(utilisateur);
//           }
//           return utilisateur;
//    }
//
//    public List<Projet> getProjetsByUtilisateurId(Long idUtilisateur) {
//        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur).orElse(null);
//        if (utilisateur != null) {
//            return utilisateur.getProjet();
//        }
//        return utilisateur.getProjet();
//    }
//
//    @Override
//    public Utilisateur updateUser(Utilisateur utilisateur, Long id) {
//           utilisateurRepository.findById(id);
//           Utilisateur utilisateurUpdate = new Utilisateur();
//           utilisateurUpdate.setIdUtilisateur(id);
//           utilisateurUpdate.setEmail(utilisateur.getEmail());
//           utilisateurUpdate.setMotDePasse(utilisateur.getMotDePasse());
//           return utilisateurRepository.save(utilisateurUpdate);
//    }
//
//    @Override
//    public void deleteUser(Long id) {
//           utilisateurRepository.deleteById(id);
//    }
//
//
//    public String saveCompte( String email, String motDePasse) {
//        Utilisateur utilisateur=utilisateurRepository.findByEmailAndMotDePasse(email, motDePasse);
//        utilisateur.setEmail(email);
//        utilisateur.setMotDePasse(motDePasse);
//
//        if (utilisateur.getEmail().equals(email) && utilisateur.getMotDePasse().equals(motDePasse)) {
//            System.out.println("utilisateur n'existe pas");
//        } else {
//            utilisateur.getEmail();
//            utilisateur.getMotDePasse();
//        }
//        utilisateurRepository.save(utilisateur);
//        return null;
//    }
//
//
//    // methode avec passwordEncoder
////    public Utilisateur saveComptes( String email, String motDePasse) {
////        Optional<Utilisateur> utilisateur1=utilisateurRepository.findByEmail(email);
//////        Utilisateur utilisateur2=utilisateurRepository.findByEmailAndMotDePasse(email,motDePasse);
////
////        if(utilisateur1.isPresent()) throw new RuntimeException("L'utilisateur existe a déjà!");
////        Utilisateur utilisateur=new Utilisateur();
////        System.out.println("voici les identifiants de l'utilisateur:"+email+" et mot de passe :"+motDePasse);
////        utilisateur.setEmail(email);
////        utilisateur.setMotDePasse(bCryptPasswordEncoder.encode(motDePasse));
////        utilisateurRepository.save(utilisateur);
////        return utilisateur;
////    }
//
//    public   Utilisateur  connexion( String email, String motDePasse) {
//        Utilisateur utilisateur=utilisateurRepository.findByEmailAndMotDePasse(email, motDePasse);
//
//
//        if (utilisateur.getEmail().equals(email) || utilisateur.getMotDePasse().equals(motDePasse)){
//            System.out.println("Le connexion reussi avec le mail"+utilisateur.getEmail()+
//                    " et le mots de passe" +utilisateur.getMotDePasse());
//            return utilisateur;
//        } else {
//            System.out.println("utilisateur n'existe pas");
//
//        }
//
//        return null;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
//}
