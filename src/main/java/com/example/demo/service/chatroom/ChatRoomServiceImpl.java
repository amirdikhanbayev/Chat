package com.example.demo.service.chatroom;

import com.example.demo.model.ChatRoom;
import com.example.demo.model.Users;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.service.get.GetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private GetService getService;

    @Override
    public ChatRoom create(String name, Users user){
        ChatRoom chat_room = new ChatRoom();
        chat_room.setName(name);
        List<Users> users = new ArrayList<>();
        users.add(getService.getCurrentUser());
        users.add(user);
        chat_room.setUsers(users);
        return chatRoomRepository.save(chat_room);
    }
    @Override
    public String delete(Long id){
        chatRoomRepository.deleteById(id);
        return "User has been deleted";
    }
    @Override
    public ChatRoom findByName(String name){
        return chatRoomRepository.findByName(name);
    }
    @Override
    public ChatRoom changeName(String name){
        ChatRoom chat_room = chatRoomRepository.findByName(name);
        chat_room.setName(name);
        return chatRoomRepository.save(chat_room);
    }
    @Override
    public List<Users> ListUserInChat(String chatName){
        ChatRoom chatRoom = findByName(chatName);
        List<Users> users = chatRoom.getUsers();
        return users;
    }


}
