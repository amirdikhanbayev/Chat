package com.example.demo.service.messagedto;

import com.example.demo.dto.MessageDto;
import com.example.demo.model.Message;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.service.get.GetService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class MessageDtoMappingServiceImpl implements MessageDtoMappingService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GetService getService;

    @Override
    public Message DtoToMessage(MessageDto messageDto){
        Message message = new Message();
        message.setId(messageDto.getId());
        message.setChatRoom(chatRoomRepository
                .findById(messageDto.getChatRoomId())
                .orElseThrow(()-> new EntityNotFoundException()));
        message.setSender(getService.getCurrentUser());
        message.setRecipient(userService
                .findById(messageDto.getRecipientId())
                .orElseThrow(()-> new EntityNotFoundException()));
        message.setContent(messageDto.getContent());
        message.setDatetime(LocalDateTime.now());
        return message;
    }

    @Override
    public MessageDto MessageToDto(Message message){
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setChatRoomId(message.getChatRoom().getId());
        messageDto.setSenderId(message.getSender().getId());
        messageDto.setRecipientId(message.getRecipient().getId());
        messageDto.setContent(message.getContent());
        messageDto.setDateTime(message.getDatetime());
        return messageDto;
    }
}
