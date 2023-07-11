package com.example.demo.service.message;

import com.example.demo.model.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessageService {


    void delete(Long id);

    Message send(String recipient_username, String content, String name);

//    List<Message> getMyMessages();
}
