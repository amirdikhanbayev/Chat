package com.example.demo.service.chatroom;

import com.example.demo.model.Chat_room;
import com.example.demo.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Override
    public Chat_room create(String name){
        Chat_room chat_room = new Chat_room();
        chat_room.setName(name);
        return chatRoomRepository.save(chat_room);
    }
    @Override
    public String delete(Long id){
        chatRoomRepository.deleteById(id);
        return "User has been deleted";
    }
    @Override
    public Chat_room findByName(String name){
        return chatRoomRepository.findByName(name);
    }
    @Override
    public Chat_room changeName(String name){
        Chat_room chat_room = chatRoomRepository.findByName(name);
        chat_room.setName(name);
        return chatRoomRepository.save(chat_room);
    }


}
