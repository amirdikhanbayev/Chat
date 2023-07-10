package com.example.demo.service.chatroom;

import com.example.demo.model.Chat_room;

public interface ChatRoomService {
    Chat_room create(Chat_room chat_room);

    String delete(Long id);
}
