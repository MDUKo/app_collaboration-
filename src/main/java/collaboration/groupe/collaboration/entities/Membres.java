package collaboration.groupe.collaboration.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "membres")
public class Membres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMembres;

    @Column(name = "nom", unique = false,nullable = false)
    private String nom;

    @Column(name = "prenom", unique = false, nullable = false)
    private String prenom;

    @Column(name = "email",unique = true)
    private String email;

    @ManyToMany(mappedBy = "membres")
    private List<Projet> projet=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "idUtilisateur" +
            "")//idUtilisateur
    private Utilisateur utilisateur;

    @OneToOne
    private Roles roles;

    @ManyToOne
    @JoinColumn(name = "tache")
    private Tache tache;

}
