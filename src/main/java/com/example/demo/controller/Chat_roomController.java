package com.example.demo.controller;

import com.example.demo.service.chatroom.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatroom")
public class Chat_roomController {
    @Autowired
    private ChatRoomService chatRoomService;
    @GetMapping("/delete")
    public String delete(Long id){
        return chatRoomService.delete(id);
    }
}
