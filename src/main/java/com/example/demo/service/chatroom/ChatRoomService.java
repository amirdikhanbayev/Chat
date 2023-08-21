package com.example.demo.service.chatroom;

import com.example.demo.model.ChatRoom;

public interface ChatRoomService {

    ChatRoom create(String name);

    String delete(Long id);
    ChatRoom findByName(String name);

    ChatRoom changeName(String name, String newName);

}
