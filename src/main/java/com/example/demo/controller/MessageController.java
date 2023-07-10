package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/setDate")
    public Optional<Message> setDate(LocalDateTime date, Long id){
        return messageService.setDate(date,id);
    }

}
