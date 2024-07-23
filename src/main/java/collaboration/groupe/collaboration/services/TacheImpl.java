package collaboration.groupe.collaboration.services;

import collaboration.groupe.collaboration.entities.Tache;
import collaboration.groupe.collaboration.repository.TacheRepository;
import collaboration.groupe.collaboration.services.collabServices.TacheService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TacheImpl implements TacheService {

    @Autowired
    private TacheRepository tacheRepository;
    @Override
    public Tache createTask(Tache tache) {
//           Tache tacheCreate=new Tache();
//           tacheCreate.setTitre_tache(tache.getTitre_tache());
//           tacheCreate.setDescription_tache(tache.getDescription_tache());
           return tacheRepository.save(tache);
    }

    @Override
    public Tache findTaskById(Long id) {

           return tacheRepository.findById(id).orElse(null);
    }

    @Override
    public Tache updateTask(Tache tache, Long id) {
            tacheRepository.findById(id);
            Tache tacheUpdate=new Tache();
            tacheUpdate.setIdTache(id);
            tacheUpdate.setMembres(tache.getMembres());
            tacheUpdate.setTitre_tache(tache.getTitre_tache());
            tacheUpdate.setDescription_tache(tache.getDescription_tache());
            return tacheRepository.save(tache) ;
    }

    @Override
    public List<Tache> findAll() {
           return tacheRepository.findAll();
    }

    @Override
    public void deleteTask(Long id) {
           tacheRepository.deleteById(id);

    }
}
