package com.example.demo.service.message;

import com.example.demo.model.Message;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MessageService {
    Optional<Message> setDate(LocalDateTime date, Long id);
}
