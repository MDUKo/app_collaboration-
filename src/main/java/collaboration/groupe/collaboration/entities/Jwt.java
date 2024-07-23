package collaboration.groupe.collaboration.entities;

import collaboration.groupe.collaboration.entities.RefreshToken;
import collaboration.groupe.collaboration.entities.Utilisateur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jwt")
public class Jwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String valeur;
    private boolean desactive;

    private boolean expire;

    private String value;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private RefreshToken refreshToken;



    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private Utilisateur user;



}
