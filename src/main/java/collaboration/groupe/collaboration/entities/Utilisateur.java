package collaboration.groupe.collaboration.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

    @Column(name = "nom", unique = false,nullable = true)
    private String nom;

    @Column(name = "prenom", unique = false, nullable = false)
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "motdepasse")
    private String motDePasse;

    private boolean actif=false;

    @OneToMany(mappedBy = "utilisateur")
    private List<Projet> projet;


    @OneToOne(cascade = CascadeType.ALL)
    private Roles roles;

    @OneToMany
    @JoinColumn(name = "message")
    private Collection<Message> message;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private  List<Membres> membres=new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.roles.getNomrole()));
    }

    @Override
    public String getPassword() {
        return this.motDePasse;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isEnabled() {
        return this.actif;
    }
}
