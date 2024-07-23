package collaboration.groupe.collaboration.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Utilisateur sender;
    @ManyToOne
    private Utilisateur recipient;
    private String messageContent;

    public Message(Utilisateur sender, Utilisateur recipient, String messageContent) {
        this.sender = sender;
        this.recipient = recipient;
        this.messageContent = messageContent;
    }




}
