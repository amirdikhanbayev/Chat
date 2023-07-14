package com.example.demo.dto;


import com.example.demo.model.Message;

public interface MessageDtoMappingService {
    Message DtoToMessage(MessageDto messageDto);

    MessageDto MessageToDto(Message message);
}
