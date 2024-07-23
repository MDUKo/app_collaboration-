package collaboration.groupe.collaboration.repository;

import collaboration.groupe.collaboration.entities.Projet;
import collaboration.groupe.collaboration.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
}
