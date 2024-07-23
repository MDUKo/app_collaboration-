//package collaboration.groupe.collaboration.controller;
//
//import collaboration.groupe.collaboration.entities.Projet;
//import collaboration.groupe.collaboration.entities.Utilisateur;
//import collaboration.groupe.collaboration.services.UtilisateurImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/utilisateurs")
//public class UtilisateurController {
//
//        @Autowired
//        private UtilisateurImpl utilisateurService;
//
////        @Autowired
////        public UtilisateurController(UtilisateurImpl utilisateurService) {
////            this.utilisateurService = utilisateurService;
////        }
//
//        @PostMapping(value = "/create")
//        public ResponseEntity<Utilisateur> createUser(@RequestBody Utilisateur utilisateur) {
//           /* Utilisateur createdUtilisateur =*/
//            return new ResponseEntity<>(utilisateurService.createUser(utilisateur), HttpStatus.CREATED);
//        }
//
//        @GetMapping("/{idu}")
//        public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable("idu") Long idUtilisateur) {
//            Utilisateur utilisateur = utilisateurService.findUser(idUtilisateur);
//            if (utilisateur != null) {
//                System.out.println("l'utilisateur à le id"+idUtilisateur);
//                return new ResponseEntity<>(utilisateur, HttpStatus.OK);
//            }
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        @GetMapping("/{idu}/projets")
//        public ResponseEntity<List<Projet>> findProjetsByUtilisateurId(@PathVariable("idu") Long idUtilisateur) {
//            List<Projet> projets = utilisateurService.getProjetsByUtilisateurId(idUtilisateur);
//            if (projets != null) {
//                return new ResponseEntity<>(projets, HttpStatus.OK);
//            }
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        @PostMapping("/{idu}/projets/{idp}")
//        public ResponseEntity<Utilisateur> assignProjetToUtilisateur(@PathVariable("idu") Long idUtilisateur, @PathVariable("idp") Long idProjet) {
//
//            return new ResponseEntity<>(  utilisateurService.assignProjetToUtilisateur(idUtilisateur, idProjet)
//                    ,HttpStatus.OK);
//        }
//
////        @RequestMapping(value = "/connexion/{email}/{motDePasse}")
////        public ResponseEntity<String> connexionCompte(@RequestBody Utilisateur utilisateur,@PathVariable("email") String email,@PathVariable("motDePasse") String motDePasse){
////            if(utilisateur.getEmail().equals(email)&& utilisateur.getMotDePasse().equals(motDePasse)) {
////                utilisateurService.saveCompte(utilisateur, email, motDePasse);
////            }
////            return new ResponseEntity<>("",HttpStatus.OK) ;
////        }
//
//        @DeleteMapping(value = "/delete/{id}")
//        public ResponseEntity  deleteuser(@PathVariable("id") Long id){
//            utilisateurService.deleteUser(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//
//
//    @PostMapping("/connexion")
//    public ResponseEntity<Utilisateur> connexion(@RequestBody Utilisateur utilisateur) {
//        if (utilisateur != null) {
//            utilisateurService.connexion(utilisateur.getEmail(), utilisateur.getMotDePasse());
////            System.out.println("Le connexion reussi avec le mail"+utilisateur.getEmail()+ " et le mots de passe"+utilisateur.getMotDePasse());
//            return ResponseEntity.ok(utilisateur);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
//
//    @RequestMapping(value = "/connex")
//    public ResponseEntity<Utilisateur> saveCompte(@RequestBody Utilisateur utilisateur){
//
//        try {
//             utilisateurService.saveCompte(utilisateur.getEmail(), utilisateur.getMotDePasse());
//            return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
//        } catch (ResponseStatusException e) {
//            // Gérer l'exception levée par le service
//            return new ResponseEntity<>(null, e.getStatusCode());
//        }
//    }
//
//
//    //controleur de passwordEncoder
////    @RequestMapping(value = "/connex")
////    public ResponseEntity<Utilisateur> saveCompte(@RequestBody Utilisateur utilisateur){
////
////
////        try {
////           Utilisateur utilisateursave= utilisateurService.saveComptes(utilisateur.getEmail(), utilisateur.getMotDePasse());
////
////            return new ResponseEntity<>(utilisateursave, HttpStatus.CREATED);
////        } catch (ResponseStatusException e) {
////            // Gérer l'exception levée par le service
////            return new ResponseEntity<>(null, e.getStatusCode());
////        }
////    }
//
//    }
//
