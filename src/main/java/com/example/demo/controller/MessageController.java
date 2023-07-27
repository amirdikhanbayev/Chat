package com.example.demo.controller;

import com.example.demo.dto.MessageDto;
import com.example.demo.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/sendNew/{recipient_username}/{content}/{name}")
    public MessageDto sendWithNewChat(@PathVariable String recipient_username,
                        @PathVariable String content,
                        @PathVariable String name){
        return messageService.sendWithNewChat(recipient_username,content,name);
    }

    @GetMapping("/sendToChat/{chat_name}/{content}")
    public String sendToExistChat(@PathVariable String chat_name,@PathVariable String content){
        return messageService.sendToChat(chat_name,content,null);
    }

    @GetMapping("/getMyMesseges")
    public List<MessageDto> getMM(){
        List<MessageDto> messages = messageService.getMyMessages();
        return messages;
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String delete(@PathVariable Long id){
        messageService.delete(id);
        return "Message deleted";
    }
    @GetMapping("/sendDelayedMessageToChat/{chatroom}/{min}/{content}")
    public ResponseEntity<Void> SendDelayedMessage(@PathVariable String chatroom,
                                                     @PathVariable Long min, @PathVariable String content){
        messageService.delayedMessage(chatroom,min,content);
        return ResponseEntity.ok().build();
    }

}
