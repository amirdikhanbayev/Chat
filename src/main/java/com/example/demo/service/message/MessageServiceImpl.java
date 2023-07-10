package com.example.demo.service.message;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.message.MessageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Optional<Message> setDate(LocalDateTime date, Long id){
        Message message = messageRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
        message.setDatetime(date);
        return Optional.of(messageRepository.save(message));
    }
}
