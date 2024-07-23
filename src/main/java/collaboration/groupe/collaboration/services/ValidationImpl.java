package collaboration.groupe.collaboration.services;

import collaboration.groupe.collaboration.entities.Utilisateur;
import collaboration.groupe.collaboration.entities.Validation;
import collaboration.groupe.collaboration.repository.ValidationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@AllArgsConstructor
@Service
public class ValidationImpl {

    @Autowired
    private ValidationRepository validationRepository;


    public void saveValidation(Utilisateur utilisateur){
        Validation validation=new Validation();
        validation.setUtilisateur(utilisateur);
        Instant creation= Instant.now();
        validation.setCreation(creation);
        Instant expiration=creation.plus(10, MINUTES);
        validation.setExpiration(expiration);
        Random random=new Random();
        int randomInteger=  random.nextInt(999999);
        String code=String.format("%06d", randomInteger);
        validation.setCode(code);
        this.validationRepository.save(validation);
    }

    public Validation readInFunctionOfCode(String code){
    return this.validationRepository.findByCode(code).orElseThrow(()-> new RuntimeException("Your code it's invalid"));
    }
}
