package com.example.demo.service.chatroom;

import com.example.demo.model.Chat_room;

public interface ChatRoomService {

    Chat_room create(String name);

    String delete(Long id);
    Chat_room findByName(String name);

    Chat_room changeName(String name);
}
