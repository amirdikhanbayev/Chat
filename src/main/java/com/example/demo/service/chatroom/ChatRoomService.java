package com.example.demo.service.chatroom;

import com.example.demo.model.ChatRoom;
import com.example.demo.model.Users;

import java.util.List;

public interface ChatRoomService {

    ChatRoom create(String name, Users user);

    String delete(Long id);
    ChatRoom findByName(String name);

    ChatRoom changeName(String name);

    List<Users> ListUserInChat(String chatName);
}
