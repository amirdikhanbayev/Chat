package com.example.demo.controller;

import com.example.demo.model.ChatRoom;
import com.example.demo.service.chatroom.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatroom")
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        return chatRoomService.delete(id);
    }

    @GetMapping("changeName/{name}")
    public ChatRoom changeName(@PathVariable String name){
        return chatRoomService.changeName(name);
    }
}
