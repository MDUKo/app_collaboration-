package collaboration.groupe.collaboration.services;

import collaboration.groupe.collaboration.entities.Utilisateur;
import collaboration.groupe.collaboration.entities.Validation;
import collaboration.groupe.collaboration.repository.UtilisateurRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;


@AllArgsConstructor
@Service
public class NotificationImpl {

// cette partie est dediée pour l'envoie de mail via un serveur local docker
    private JavaMailSender javaMailSender;
    private UtilisateurRepository utilisateurRepository;

    //    public void sendMail(Validation validation) { //je vais changé à ma guise
    public void sendMail(Utilisateur utilisateur) { //je vais changé à ma guise
//        Utilisateur utilisateur=new Utilisateur();
        String saveuser = utilisateurRepository.findByEmail(utilisateur.getEmail()).toString();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(saveuser);//il a mis no-reply@chillo.tech mais il faut qu'on appel un utilisateur ic
//            mailMessage.setTo(validation.getUtilisateur().getEmail());
        mailMessage.setTo(utilisateur.getEmail());
        mailMessage.setSubject("votre code d'activation");

        Validation validation = new Validation();

        String texte = String.format("Bonjour %s, <br /> votre code d'action est %s, A bientot",
//                    validation.getUtilisateur().getNom(),
                utilisateur.getNom(),
                validation.getCode());
//                    validation.getCode());
        mailMessage.setText(texte);
        javaMailSender.send(mailMessage);

    }


    public void sendMailWithAttachment(String to, String subject, String text, String pathToAttachment){
        try {
            MimeMessage message= javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.addAttachment("attachment.pdf", new File(pathToAttachment));
            helper.setText(text,true);
            javaMailSender.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void sendMessageWithAttachmentToUser(Long userId, String subject, String text,String pathAttachment){
        Utilisateur recipient=utilisateurRepository.findById(userId).orElseThrow(
                ()->new RuntimeException("Utilisateur non trouvé")
        );
        sendMailWithAttachment(recipient.getEmail(),subject,text,pathAttachment);
    }
}
