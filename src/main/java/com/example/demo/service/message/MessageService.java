package com.example.demo.service.message;

import com.example.demo.dto.MessageDto;
import com.example.demo.model.Message;

import java.util.List;


public interface MessageService {


    void delete(Long id);

    MessageDto sendWithNewChat(String recipient_username, String content, String name);

    String sendToChat(String chat_name, String content);

    Message creatMessage(String content, String recipient_username);

    List<MessageDto> getMyMessages();
}
