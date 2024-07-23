package collaboration.groupe.collaboration.controller;

import collaboration.groupe.collaboration.entities.Membres;
import collaboration.groupe.collaboration.services.MembreImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/membre")
public class MembreController {

    @Autowired
    private MembreImpl membreService;

    //Creér un membre
    @PostMapping(value = "/createmembre", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Membres> createMembre(@RequestBody Membres membres){

           return new ResponseEntity<>(membreService.createMember(membres), HttpStatus.OK);
    }

    //Créer un membre en y ajoutant sa tache et son role
    @PostMapping(value = "/createmembres/{idr}/{idt}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Membres> createMembres(@RequestBody Membres membres, @PathVariable("idr") Long idr, @PathVariable("idt") Long idt){

           return new ResponseEntity<>(membreService.createMembers(membres,idr,idt), HttpStatus.OK);
    }

    //Ajouter un role à un membre
    @PostMapping(value = "/add/{idm}/{idr}")
    public ResponseEntity<String> addRoleToMembre(@PathVariable("idm") Long idMembres,@PathVariable("idr") Long idRole) {
           membreService.addRoleToMembre(idMembres, idRole);

           return new ResponseEntity<>("Role ajouté au  membre avec succès", HttpStatus.OK);
    }

    //Ajouter une tache à un membre
    @PostMapping(value = "/addt/{idm}/{idt}")
    public ResponseEntity<String> addTacheToMembre(@PathVariable("idm") Long idMembres,@PathVariable("idt") Long idTache) {

           return new ResponseEntity<>("tache ajouté au  membre avec succès", HttpStatus.OK);
    }

    //Modifier un membre
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Membres> updateMembre(@RequestBody Membres membres,@PathVariable("id") Long id){
           if(id==null){
              throw new RuntimeException("ce membre n'existe pas"+id);
           }
          return new ResponseEntity<>(membreService.updateMember(membres, id), HttpStatus.OK);
    }

    //Rechercher tous les membres
    @GetMapping(value = "/findAll")
    public ResponseEntity<List<Membres>> findAllMember(){
           List<Membres> membres=membreService.findAllMember();
           return new ResponseEntity<>(membres,HttpStatus.OK);
    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<Membres> find(@PathVariable("id") Long id){
           return new ResponseEntity<>(membreService.findMember(id),HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteMember(Long id){
           membreService.deletemember(id);
           System.out.println("Le membre avec l'identifiant"+id+"a été supprimé avec success");
           return new ResponseEntity<>(HttpStatus.OK);
    }
}
