package collaboration.groupe.collaboration.entities;

import collaboration.groupe.collaboration.enums.TypeRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Enumerated(EnumType.STRING)
    private TypeRole nomrole;

//    @Column(name = "nomrole", unique = true)
//    @Enumerated(EnumType.STRING)
//    private String nom_role;

//    @ManyToOne
//    @JoinColumn(name = "tache")
//    private Tache tache;










}
