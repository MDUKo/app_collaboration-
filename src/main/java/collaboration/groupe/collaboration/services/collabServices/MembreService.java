package collaboration.groupe.collaboration.services.collabServices;

import collaboration.groupe.collaboration.entities.Membres;

import java.util.List;

public interface MembreService {

    public Membres createMember(Membres membres);

    public Membres findMember(Long id);

    public List<Membres> findAllMember();

    public Membres updateMember(Membres membres, Long id);

    public void deletemember(Long id);
}
