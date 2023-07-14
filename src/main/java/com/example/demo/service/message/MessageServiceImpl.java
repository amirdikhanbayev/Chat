package com.example.demo.service.message;

import com.example.demo.dto.MessageDto;
import com.example.demo.dto.MessageDtoMappingService;
import com.example.demo.model.ChatRoom;
import com.example.demo.model.Message;
import com.example.demo.model.Users;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.chatroom.ChatRoomService;
import com.example.demo.service.get.GetService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GetService getService;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private MessageDtoMappingService messageDtoMappingService;

    @Override
    public void delete(Long id){
        messageRepository.deleteById(id);
    }

    @Override
    public MessageDto sendWithNewChat(String recipient_username, String content, String name){
        Message message = send(content, recipient_username);
        Users recipient = userService.findByUsername(recipient_username)
                .orElseThrow(()-> new EntityNotFoundException());
        ChatRoom chatRoom = chatRoomService.create(name, recipient);
        message.setChatRoom(chatRoom) ;
        recipient.getChatRooms().add(chatRoom);
        messageRepository.save(message);
        return messageDtoMappingService.MessageToDto(message);
    }

    @Override
    public String sendToChat(String chat_name, String content){
        List<Users> users = userService.UsersInChat(
                chatRoomService.findByName(chat_name));
        for (int i = 0; i < users.size(); i++){
            String username = users.get(i).getUsername();
            send(content, username);
        }
        return "Messages sanded";
    }
    @Override
    public Message send(String content, String recipient_username){
        Message message = new Message();
        message.setSender(getService.getCurrentUser());
        Users recipient = userService.findByUsername(recipient_username)
                .orElseThrow(()-> new EntityNotFoundException());
        message.setRecipient(recipient);
        getService.getCurrentUser().setOnline(true);
        message.setContent(content);
        message.setDatetime(LocalDateTime.now());
        return message;
    }

    @Override
    public List<MessageDto> getMyMessages(){
        List<MessageDto> listDto = new ArrayList<>();
        List<Message> listMessages = messageRepository.findAllByRecipientId(getService.getCurrentUser().getId());
        for (int i = 0; i < listMessages.size(); i++) {
          listDto.add(messageDtoMappingService.MessageToDto(listMessages.get(i)));
        }
        return listDto;
    }


}
