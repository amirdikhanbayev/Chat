package com.example.demo.service.message;

import com.example.demo.dto.MessageDto;
import com.example.demo.model.ChatRoom;
import com.example.demo.model.Message;
import com.example.demo.model.Users;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.chatroom.ChatRoomService;
import com.example.demo.service.get.GetService;
import com.example.demo.service.messagedto.MessageDtoMappingService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
    public void delete(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public MessageDto sendWithNewChat(String recipientUsername, String content, String name) {
        Message message = creatMessage(content, recipientUsername);
        Users recipient = userService.findByUsername(recipientUsername)
                .orElseThrow(EntityNotFoundException::new);
        ChatRoom chatRoom = chatRoomService.create(name);
        message.setChatRoom(chatRoom);
        recipient.getChatRooms().add(chatRoom);
        messageRepository.save(message);
        return messageDtoMappingService.MessageToDto(message);
    }

    @Override
    @Transactional
    public String sendToChat(String chatName, String content) {
        List<Users> users = userService.UsersInChat(
                chatRoomService.findByName(chatName));
        if (users.isEmpty()) {
            return "Bad chat name, or no users in chat";
        }
        for (int i = 0; i < users.size(); i++) {
            String username = users.get(i).getUsername();
            Message message;
            message = creatMessage(content, username);
            message.setChatRoom(chatRoomService.findByName(chatName));
            messageRepository.save(message);
        }
        return "Messages sanded";

    }
    @Override
    public String sendToChatSep(String chatName, String content) {
        List<Users> users = userService.UsersInChat(
                chatRoomService.findByName(chatName));
        if (users.isEmpty()) {
            return "Bad chat name, or no users in chat";
        }
        for (int i = 0; i < users.size(); i++) {
            String username = users.get(i).getUsername();
            Message message;
            message = creatMessage(content, username);
            message.setChatRoom(chatRoomService.findByName(chatName));
            messageRepository.save(message);
        }
        return "Messages sanded";
    }

    @Override
    public Message creatMessage(String content, String recipientUsername) {
        Message message = new Message();
        message.setSender(getService.getCurrentUser());
        Optional<Users> recipient = userService.findByUsername(recipientUsername);
            message.setRecipient(recipient
                    .orElseThrow(() -> new EntityNotFoundException("NaN")));
            getService.getCurrentUser().setOnline(true);
            message.setContent(content);
            message.setDatetime(LocalDateTime.now());
            return message;
    }

    @Override
    public List<MessageDto> getMyMessages() {
        List<MessageDto> listDto = new ArrayList<>();
        List<Message> listMessages = messageRepository.findAllByRecipientId(getService.getCurrentUser().getId());
        if (listMessages.isEmpty()) {
            System.out.println("No Messages");
            return null;
        }
        for (int i = 0; i < listMessages.size(); i++) {
            listDto.add(messageDtoMappingService.MessageToDto(listMessages.get(i)));
        }
        return listDto;
    }

    @Override
    public Mono<Void> delayedMessage(String chatroom, Long min, String content) {
        return completableFutureProcess(chatroom, min, content);
    }

    private Mono<Void> completableFutureProcess(String chatroom, Long min, String content) {
        CompletableFuture<Void> voidMethod = CompletableFuture.runAsync(() -> {
                    processMessage(chatroom, min, content);
                }
        );
        return Mono.fromFuture(voidMethod);
    }

    public void processMessage(String chatroom, Long min, String content) {
        try {
            TimeUnit.MINUTES.sleep(min);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sendToChat(chatroom, content);
    }
}
