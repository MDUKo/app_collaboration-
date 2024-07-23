package collaboration.groupe.collaboration.services;


import collaboration.groupe.collaboration.entities.Membres;
import collaboration.groupe.collaboration.entities.Roles;
import collaboration.groupe.collaboration.entities.Tache;
import collaboration.groupe.collaboration.repository.MembreRepository;
import collaboration.groupe.collaboration.repository.RoleRepository;
import collaboration.groupe.collaboration.repository.TacheRepository;
import collaboration.groupe.collaboration.services.collabServices.MembreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MembreImpl implements MembreService {

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private RoleRepository rolesRepository;

    @Autowired
    private TacheRepository tacheRepository;
    @Override
    public Membres createMember(Membres membres) {
           Membres membresCreated= new Membres();
           membresCreated.setNom(membres.getNom());
           membresCreated.setPrenom(membres.getPrenom());
           membresCreated.setEmail(membres.getEmail());
           membresCreated.setRoles(membres.getRoles());
           membresCreated.setTache(membres.getTache());
           return membreRepository.save(membresCreated);
    }
    public Membres createMembers(Membres membres, Long idr,Long idt) {
           Roles role= rolesRepository.findById(idr).orElse(null);
           Tache tache= tacheRepository.findById(idt).orElse(null);
           Membres membresCreated= new Membres();
           membresCreated.setNom(membres.getNom());
           membresCreated.setPrenom(membres.getPrenom());
           membresCreated.setEmail(membres.getEmail());
           membresCreated.setRoles(role);
           membresCreated.setTache(tache);
           return membreRepository.save(membresCreated);
    }
    public void addRoleToMembre(Long idMembres, Long idRole) {
            Membres membre = membreRepository.findByIdMembres(idMembres);
            Roles role = rolesRepository.findById(idRole).orElse(null);
            if (membre != null && role != null) {
                membre.setRoles(role);
                membreRepository.save(membre);
            }
    }

    public void addTacheToMembre(Long idMembres, Long idTache) {
            Membres membre = membreRepository.findByIdMembres(idMembres);
            Tache tache = tacheRepository.findById(idTache).orElse(null);
            membre.setTache(tache);

//        if (membre != null && tache != null) {
//
//        }
            membreRepository.save(membre);
    }

    @Override
    public Membres findMember(Long id) {
           Membres membres= membreRepository.findByIdMembres(id);
           return membres;
    }

    @Override
    public List<Membres> findAllMember() {
           List<Membres> membresAll=membreRepository.findAll();
           return membresAll;
    }

    @Override
    public Membres updateMember(Membres membres, Long id) {
            membreRepository.findByIdMembres(id);
            Membres membresUpdate=new Membres();
            membresUpdate.setIdMembres(id);
            membresUpdate.setNom(membres.getNom());
            membresUpdate.setEmail(membres.getEmail());
            membresUpdate.setPrenom(membres.getPrenom());
            membresUpdate.setTache(membres.getTache());
            membresUpdate.setRoles(membres.getRoles());
            membreRepository.save(membresUpdate);
            return membresUpdate;
    }

    @Override
    public void deletemember(Long id) {
            if(id!=null){
                membreRepository.deleteById(id);
            }else {
                throw new RuntimeException("Le membre avec ce id:"+id +"n'existe pas");
            }

    }
}
