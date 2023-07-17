package com.example.demo.service.user;

import com.example.demo.model.ChatRoom;
import com.example.demo.model.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Users create(Users user);

    Users changeUsername(Long id, String username);

    String deleteUser(Long id);

    Optional<Users> findByUsername(String username);


    Optional<Users> findById(Long id);

    List<Users> listAll();

    void online(Long id);

    void offline(Long id);

    List<Users> listAllOnline(boolean online);


    Users joinToChatRoom(String chatRoomName);


    List<Users> UsersInChat(ChatRoom chatRoom);
}
