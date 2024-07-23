package collaboration.groupe.collaboration.controller;

import collaboration.groupe.collaboration.entities.Tache;
import collaboration.groupe.collaboration.services.TacheImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taches")
public class TacheController {

        private TacheImpl tacheService;

        @Autowired
        public TacheController(TacheImpl tacheService) {
            this.tacheService = tacheService;
        }

        @PostMapping(value = "/create")
        public ResponseEntity<Tache> createTache(@RequestBody Tache tache) {
            Tache createdTache = tacheService.createTask(tache);
            return new ResponseEntity<>(createdTache, HttpStatus.CREATED);
        }

        @GetMapping("/{idt}")
        public ResponseEntity<Tache> getTacheById(@PathVariable("idt") Long idTache) {
            Tache tache = tacheService.findTaskById(idTache);
            if (tache != null) {
                return new ResponseEntity<>(tache, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @GetMapping
        public ResponseEntity<List<Tache>> getAllTaches() {
            List<Tache> taches = tacheService.findAll();
            return new ResponseEntity<>(taches, HttpStatus.OK);
        }

        @PutMapping("/{idt}")
        public ResponseEntity<Tache> updateTache( @RequestBody Tache tache,@PathVariable("idt") Long idTache) {
            Tache updatedTache = tacheService.updateTask( tache,idTache);
            if (updatedTache != null) {
                return new ResponseEntity<>(updatedTache, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @DeleteMapping("/{idt}")
        public ResponseEntity<Void> deleteTache(@PathVariable("idt") Long idTache) {
            tacheService.deleteTask(idTache);
            return new ResponseEntity<>(HttpStatus.OK);
        }


    }

