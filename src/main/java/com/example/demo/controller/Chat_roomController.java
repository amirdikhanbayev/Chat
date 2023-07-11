package com.example.demo.controller;

import com.example.demo.model.Chat_room;
import com.example.demo.service.chatroom.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatroom")
public class Chat_roomController {
    @Autowired
    private ChatRoomService chatRoomService;
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){

        return chatRoomService.delete(id);
    }

    @GetMapping("changeName/{name}")
    public Chat_room changeName(@PathVariable String name){
        return chatRoomService.changeName(name);
    }
}
