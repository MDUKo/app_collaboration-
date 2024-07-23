package collaboration.groupe.collaboration.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projet")
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProjet;

    @Column(name = "titre", unique = true)
    private String titre;

    @Column(name = "description_projet", unique = true)
    private String description_projet;

    @Column(name = "datecreation")
    private Date date_creation;

    @Column(name = "datedebut")
    private String date_debut;

    @Column(name = "datefin")
    private String date_fin;

    @ManyToMany(cascade ={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable( name ="projet_membre" ,joinColumns=@JoinColumn (name = "projet_id"),
            inverseJoinColumns = @JoinColumn(name ="membre_id"))
    private List<Membres> membres=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "utilisateur")
    private Utilisateur utilisateur;

}
