package collaboration.groupe.collaboration.services;

import collaboration.groupe.collaboration.entities.Message;
import collaboration.groupe.collaboration.entities.Utilisateur;
import collaboration.groupe.collaboration.repository.MessageRepository;
import collaboration.groupe.collaboration.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageImpl {

    @Autowired
    private final UtilisateurRepository utilisateurRepository;

    private final MessageRepository messageRepository;

    public MessageImpl(UtilisateurRepository utilisateurRepository, MessageRepository messageRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.messageRepository = messageRepository;
    }

    public void sendMessage(Long senderId, Long recipientId, String messageContent){
        Utilisateur sender= utilisateurRepository.findById(senderId).orElseThrow(()->
                new RuntimeException("No sender with this id found"));
        Utilisateur recipient= utilisateurRepository.findById(recipientId).orElseThrow(()->
                new RuntimeException("No recipient with this id found"));

        Message message= new Message(sender, recipient, messageContent);
        messageRepository.save(message);
    }

    public List<Message> getMessages(Long userId){
        Utilisateur user= utilisateurRepository.findById(userId).orElseThrow(()->
                new RuntimeException("no message has been found"));
        return messageRepository.findBySenderOrRecipient(user,user);
    }





}
