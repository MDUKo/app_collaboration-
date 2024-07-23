package collaboration.groupe.collaboration.repository;

import aj.org.objectweb.asm.commons.Remapper;
import collaboration.groupe.collaboration.entities.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface JwtRepository extends JpaRepository<Jwt,Long> {

    public Optional<Jwt> findByValue(String valeur);

    public Optional<Jwt> findByValueAndDesactiveAndExpire(String valeur, boolean desactive, boolean expire);

    @Query("from Jwt  j where j.expire= :expire and j.desactive= :desactive and j.user.email= :email")
    public Optional<Jwt> findByUserValidToken(String email, boolean desactive, boolean expire);
     @Query("from Jwt  j where  j.user.email= :email")
    public Stream<Jwt> findByUser(String email);


}
