package collaboration.groupe.collaboration.controller;

import collaboration.groupe.collaboration.entities.Message;
import collaboration.groupe.collaboration.services.MessageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/messages")
public class MessageController {

    private final MessageImpl message;

    public MessageController(MessageImpl message) {
        this.message = message;
    }

    @RequestMapping("/transt")
    public String fileString(){
        return"html/message";
    }

    @PostMapping(path = "/send/{sender}/{recipient}/{content}")
//    @PostMapping(path = "/send")
    public String sendMessage(@PathVariable("sender")  Long senderId, @PathVariable("recipient") Long recipientId, @PathVariable("content") String messageContent){
        message.sendMessage(senderId, recipientId, messageContent);

        return "html/message";
    }

    @GetMapping(path = "/user/{userId}")
    public List<Message> getMessages(@PathVariable Long userId){
        return message.getMessages(userId);
    }




}
