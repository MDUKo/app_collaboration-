package collaboration.groupe.collaboration.repository;

import collaboration.groupe.collaboration.entities.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface ValidationRepository extends JpaRepository<Validation,Long> {
    Optional<Validation> findByCode(String code);
}
