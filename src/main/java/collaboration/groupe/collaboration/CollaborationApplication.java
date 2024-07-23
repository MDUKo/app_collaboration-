package collaboration.groupe.collaboration;

import collaboration.groupe.collaboration.security.JwtFilter;
import collaboration.groupe.collaboration.security.PasswordEncoderImpl;
import collaboration.groupe.collaboration.security.SecurityConfig;
import collaboration.groupe.collaboration.services.collabServices.UtilisateurService;
import collaboration.groupe.collaboration.services.jwt.JwtService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CollaborationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollaborationApplication.class, args);
    }


//    CommandLineRunner start(UtilisateurService utilisateurService){
//        return null;
//    }
//    @Bean
//    BCryptPasswordEncoder getBCPE(){
//        return new BCryptPasswordEncoder();
//    }

}
