package com.example.demo.service.message;

import com.example.demo.model.Message;


import java.util.List;


public interface MessageService {


    void delete(Long id);

    Message sendWithNewChat(String recipient_username, String content, String name);

    String sendToChat(String chat_name, String content);

    Message send(String content, String recipient_username);

    List<Message> getMyMessages();
}
