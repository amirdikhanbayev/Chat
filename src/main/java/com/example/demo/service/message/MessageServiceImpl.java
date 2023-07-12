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
    public Message sendWithNewChat(String recipient_username, String content, String name){
        Message message = send(content, recipient_username);
        Users recipient = userService.findByUsername(recipient_username)
                .orElseThrow(()-> new EntityNotFoundException());
        message.setChatRoomId(chatRoomService.create(name, recipient));
        return messageRepository.save(message);
    }

    @Override
    public String sendToChat(String chat_name, String content){
        List<Users> users = chatRoomService.ListUserInChat(chat_name);
        for (int i = 0; i < users.size(); i++){
            System.out.println("dfriemifwemfigeirmfiewmfrem");
            String username = users.get(i).getUsername();
            send(content, username);
        }
        return "Messages sanded";
    }
    @Override
    public Message send(String content, String recipient_username){
        Message message = new Message();
        message.setSenderId(getService.getCurrentUser());
        Users recipient = userService.findByUsername(recipient_username)
                .orElseThrow(()-> new EntityNotFoundException());
        message.setRecipientId(recipient);
        getService.getCurrentUser().setOnline(true);
        message.setContent(content);
        message.setDatetime(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMyMessages(){
       Long id = getService.getCurrentUser().getId();
       return messageRepository.findMessageByRecipientIdId(id);
    }


}
