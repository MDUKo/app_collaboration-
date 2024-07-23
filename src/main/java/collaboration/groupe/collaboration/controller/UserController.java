package collaboration.groupe.collaboration.controller;

import collaboration.groupe.collaboration.entities.AuthenticationDto;
import collaboration.groupe.collaboration.entities.Utilisateur;
import collaboration.groupe.collaboration.services.UserImpl;
import collaboration.groupe.collaboration.services.jwt.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserImpl userImpl;


    private JwtService jwtService;


    private AuthenticationManager authenticationManager;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/register")
    public String register(@RequestBody Utilisateur user){
            Utilisateur utilisateur= new Utilisateur();
            user.setMotDePasse(user.getPassword());
//            user.setMotDePasse(user.getMotDePasse());
            this.userImpl.register(user);
            log.info("Le resultat de l'inscription est :{}");

            return "register";
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/activation")
    public String activation(@RequestBody Map<String,String> activation){

        this.userImpl.activation(activation);
        return "activation";
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/connection")
    //Ici aussi je vais mettre Object
    public Map<String, Object> connection(@RequestBody Utilisateur utilisateur) {
        System.out.println("le resuttat est:");
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(utilisateur.getUsername(), utilisateur.getMotDePasse())
        );

        if (authentication.isAuthenticated()) {
            return this.jwtService.generate(utilisateur.getUsername());
        }

        return null;
    }
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping(path = "/connection")
//    //Ici aussi je vais mettre Object
//    public Map<String, String> connection(@RequestBody AuthenticationDto authenticationDto ) {
//        System.out.println("le resuttat est:");
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authenticationDto.username(),authenticationDto.password())
//        );
//
//        if (authentication.isAuthenticated()) {
//            return this.jwtService.generate(authenticationDto.username());
//        }
//
//        return null;
//    }
    @ResponseStatus(HttpStatus.GONE)
    @PostMapping(path = "/deconnexion")
    public String deconnexion(){

        this.jwtService.deconnexion();
        return "activation";
    }
}
