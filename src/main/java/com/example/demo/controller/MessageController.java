package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/sendNew/{recipient_username}/{content}/{name}")
    public Message Send(@PathVariable String recipient_username,
                        @PathVariable String content,
                        @PathVariable String name){
        return messageService.send(recipient_username,content,name);
    }

//    @GetMapping("/getMyMesseges")
//    public List<Message> getMM(){
//        return (List<Message>) messageService.getMyMessages();
//    }

    @DeleteMapping("/delete/{id}")
    public String delet(@PathVariable Long id){
        messageService.delete(id);
        return "Message deleted";
    }

}
