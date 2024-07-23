package collaboration.groupe.collaboration.services.collabServices;

import collaboration.groupe.collaboration.entities.Roles;

import java.util.List;

public interface RolesService {
    public Roles createRole(Roles roles);
    public Roles getRoleById(Long idRole);
    public List<Roles> getAllRoles();
    public Roles updateRole(Long idRole,Roles roles );
    public void deleteRole(Long idRole);
}
