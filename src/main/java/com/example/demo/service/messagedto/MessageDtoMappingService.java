package com.example.demo.service.messagedto;


import com.example.demo.dto.MessageDto;
import com.example.demo.model.Message;

public interface MessageDtoMappingService {
    Message DtoToMessage(MessageDto messageDto);

    MessageDto MessageToDto(Message message);
}
