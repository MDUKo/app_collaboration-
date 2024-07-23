package collaboration.groupe.collaboration.services;

import collaboration.groupe.collaboration.entities.Roles;
import collaboration.groupe.collaboration.repository.RoleRepository;
import collaboration.groupe.collaboration.services.collabServices.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleImpl implements RolesService {

    @Autowired
    private  RoleRepository rolesRepository;

    @Autowired
    public  RoleImpl(RoleRepository rolesRepository) {
            this.rolesRepository = rolesRepository;
    }

    public Roles createRole(Roles roles) {
//           Roles rolesave = new Roles();
//           rolesave.setNom_role(roles.getNom_role());
           return rolesRepository.save(roles);
    }

    public Roles getRoleById(Long idRole) {
           return rolesRepository.findById(idRole).orElse(null);
    }

    public List<Roles> getAllRoles() {
           return rolesRepository.findAll();
    }

    public Roles updateRole(Long idRole,Roles roles ) {
           Roles roleUpdate = rolesRepository.findById(idRole).orElse(null);
           if (roleUpdate != null) {
              roleUpdate.setIdRole(idRole);
              roleUpdate.setNomrole(roles.getNomrole());
              return rolesRepository.save(roleUpdate);
           }
           return roleUpdate;
    }

    public void deleteRole(Long idRole) {
           rolesRepository.deleteById(idRole);
    }
}
