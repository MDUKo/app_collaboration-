package collaboration.groupe.collaboration.services.collabServices;

import collaboration.groupe.collaboration.entities.Tache;

import java.util.List;

public interface TacheService {

    public Tache createTask(Tache tache);

    public Tache findTaskById(Long id);

    public Tache updateTask(Tache tache, Long id);

    public List<Tache> findAll();

    public void deleteTask(Long id);
}
