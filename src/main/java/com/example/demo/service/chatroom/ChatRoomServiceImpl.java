package com.example.demo.service.chatroom;

import com.example.demo.model.Chat_room;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.service.chatroom.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Override
    public Chat_room create(Chat_room chat_room){
        return chatRoomRepository.save(chat_room);
    }
    @Override
    public String delete(Long id){
        chatRoomRepository.deleteById(id);
        return "User has been deleted";
    }
}
