package collaboration.groupe.collaboration.controller;

import collaboration.groupe.collaboration.entities.Projet;
import collaboration.groupe.collaboration.entities.Utilisateur;
import collaboration.groupe.collaboration.services.ProjetImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Le controlleur de projet
@RestController
//@RequestMapping(path = "/projet")
public class ProjetController {

    @Autowired
    private ProjetImpl projetService;

       //cette méthode me permet de créer un projet
    @PostMapping(path = "/create")
    public ResponseEntity createProjet(@RequestBody Projet projet) {
         projetService.createProjets(projet);
//

        return null;
    }

       //cette méthode permet d'associer un membre à un projet par son email(disfonctionnel)
    @PostMapping(value = "/createprojet/{idP}/{email}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Projet> createProjet(@PathVariable("idP") Long idProjet, @PathVariable("email") String email){

           return new ResponseEntity<>(projetService.createProjetWithmember(idProjet, email), HttpStatus.CREATED);
    }

////1er essai
//    @PostMapping(value = "/createprojets" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity <Projet> createProjets(@RequestBody Projet projet){
//
//        Projet save=projetService.createProjetWithmember(projet.getIdProjet(), projet.getMembres().toString());
//        return new ResponseEntity<>(save, HttpStatus.CREATED);
//    }
    //1er essai
    @PostMapping(value = "/createprojets/{email}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Projet> createProjets(@RequestBody Projet projet, @PathVariable("email") String email){

        Projet save=projetService.createProjetWithmember(projet.getIdProjet(), email);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }
//2e essai
    @PostMapping(value = "/createprojetes" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Projet> createProjetes(@RequestBody Projet projet){

        Projet save=projetService.createProjetWithmembers(projet, projet.getMembres().toString());
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

         //cette méthode permet d'associer un membre à un projet par son id(fonctionnel)
    @PostMapping(value = "/membre/{idp}/{idm}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ajoutMembreProjet(@PathVariable("idp") Long idProjet, @PathVariable("idm") Long idMembre){
           projetService.addMembreToProjet(idProjet, idMembre);
           return new ResponseEntity<>("Membre ajouté au projet avec succès", HttpStatus.OK);
    }

        //cette méthode permet de rechercher un projet par son id
    @GetMapping(value = "/find/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Projet> findProjet(@PathVariable("id") Long id){
           return new ResponseEntity<>(projetService.findByIdProjet(id),HttpStatus.OK);
    }

        //cette méthode permet de lister tous les projets dejà créer
    @GetMapping(value = "/findAll/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <List<Projet>> findAllProjet(){
           return new ResponseEntity<>(projetService.getAllProjets(),HttpStatus.OK);
    }

       //cette méthode me permet de modifier un projet crée
    @PutMapping(value = "/update/{idp}")
    public ResponseEntity<Projet> updateProjet(@RequestBody Projet projet ,@PathVariable("idp") Long idprojet){
           return new ResponseEntity<>(projetService.updateProjet(projet, idprojet), HttpStatus.OK);
    }

      //cette méthode permet de supprimer un projet par son id
    @DeleteMapping("/delete/{id}")
    public void deleteprojet(@PathVariable("id") Long id){
     projetService.deleteProjet(id);
    }


}
