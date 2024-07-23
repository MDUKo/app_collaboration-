package collaboration.groupe.collaboration.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tache")
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTache;

    @Column(name = "titretache")
    private String titre_tache;

    @Column(name = "descriptiontache", nullable = true,unique = false,insertable = true)
    private  String description_tache;
//
//    @OneToMany(mappedBy = "tache")
//    private List<Roles> roles;

    @OneToMany(mappedBy = "tache")
    private Collection<Membres> membres;
}
