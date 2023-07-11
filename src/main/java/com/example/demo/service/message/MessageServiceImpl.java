package com.example.demo.service.message;

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

    @Override
    public void delete(Long id){
        messageRepository.deleteById(id);
    }

    @Override
    public Message send(String recipient_username, String content, String name){
        Message message = new Message();
        message.setContent(content);
        message.setDatetime(LocalDateTime.now());
        Users users = getService.getCurrentUser();
        users.setOnline(true);
        message.setUser(users);
        message.setUser1(userService.findByUsername(recipient_username)
                .orElseThrow(()-> new EntityNotFoundException(name)));
        message.setChat_room(chatRoomService.create(name));
        return messageRepository.save(message);
    }

//    @Override
//    public List<Message> getMyMessages(){
//       Long id = getService.getCurrentUser().getId();
//       return (List<Message>) messageRepository.getMessagesByIdIs(id);
//    }


}
