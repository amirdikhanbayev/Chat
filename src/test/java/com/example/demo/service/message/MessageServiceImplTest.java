package com.example.demo.service.message;

import com.example.demo.dto.MessageDto;
import com.example.demo.model.ChatRoom;
import com.example.demo.model.Message;
import com.example.demo.model.Users;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.chatroom.ChatRoomService;
import com.example.demo.service.get.GetService;
import com.example.demo.service.messagedto.MessageDtoMappingService;
import com.example.demo.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {
    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private MessageServiceImpl messageService;
    @Mock
    private UserService userService;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private GetService getService;
    @Mock
    private ChatRoomService chatRoomService;
    @Mock
    private MessageDtoMappingService mappingService;

    @Test
    void sendWithNewChat() {
            Users sender = new Users();
            sender.setId(1L);
            sender.setUsername("john");
            sender.setPassword("password");
            sender.setOnline(true);

            Users recipient = new Users();
            recipient.setId(2L);
            recipient.setUsername("amir");
            recipient.setPassword("password");
            recipient.setOnline(true);
            recipient.setChatRooms(new ArrayList<>());

            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setId(1L);
            chatRoom.setName("ChatRoom");

            Message message = new Message();
            message.setId(1L);
            message.setSender(sender);
            message.setRecipient(recipient);
            message.setContent("Hello!");
            message.setDatetime(LocalDateTime.now());
            message.setChatRoom(chatRoom);
            MessageDto messageDto = new MessageDto();

            when(getService.getCurrentUser()).thenReturn(sender);
            when(userService.findByUsername(recipient.getUsername()))
                    .thenReturn(Optional.of(recipient));
            when(mappingService.MessageToDto(Mockito.any(Message.class))).thenReturn(messageDto);

            MessageDto result = messageService.sendWithNewChat(recipient.getUsername(),
                    message.getContent(), chatRoom.getName());

            assertNotNull(result);
            assertEquals(messageDto, result);

    }

    @Test
    void sendToChat() {
        String old = "Messages sanded";
        Users sender = new Users();
        sender.setId(1L);
        sender.setUsername("john");
        sender.setPassword("password");
        sender.setOnline(true);
        sender.setChatRooms(new ArrayList<>());

        Users recipient = new Users();
        recipient.setId(1L);
        recipient.setUsername("amir");
        recipient.setPassword("password");
        recipient.setOnline(true);
        recipient.setChatRooms(new ArrayList<>());

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(1L);
        chatRoom.setName("ChatRoom");

        Message message = new Message();
        message.setId(1L);
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent("Hello!");
        message.setDatetime(LocalDateTime.now());
        message.setChatRoom(chatRoom);

        Mockito.when(chatRoomService.findByName(any())).thenReturn(chatRoom);
        Mockito.when(userService.UsersInChat(any())).thenReturn(List.of(sender,recipient));
        Mockito.when(userService.findByUsername(any())).thenReturn(Optional.of(recipient));
        Mockito.when(getService.getCurrentUser()).thenReturn(sender);

        String result = messageService.sendToChat(chatRoom.getName(), message.getContent());


        assertEquals(old, result);
    }
    @Test
    void creatMessage() {
        Users sender = new Users();
        sender.setId(1L);
        sender.setUsername("john");
        sender.setPassword("password");
        sender.setOnline(true);
        sender.setChatRooms(new ArrayList<>());

        Users recipient = new Users();
        recipient.setId(1L);
        recipient.setUsername("amir");
        recipient.setPassword("password");
        recipient.setOnline(true);
        recipient.setChatRooms(new ArrayList<>());

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(1L);
        chatRoom.setName("ChatRoom");

        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent("Hello!");
        message.setDatetime(LocalDateTime.now());


        when(getService.getCurrentUser()).thenReturn(sender);
        when(userService.findByUsername(any())).thenReturn(Optional.of(recipient));


        Message result = messageService.creatMessage(message.getContent(),
                recipient.getUsername());

        message.setDatetime(result.getDatetime());

        assertEquals(message,result);
    }

    @Test
    void getMyMessages() {
        Users sender = new Users();
        sender.setId(1L);
        sender.setUsername("john");
        sender.setPassword("password");
        sender.setOnline(true);
        sender.setChatRooms(new ArrayList<>());

        Users recipient = new Users();
        recipient.setId(1L);
        recipient.setUsername("amir");
        recipient.setPassword("password");
        recipient.setOnline(true);
        recipient.setChatRooms(new ArrayList<>());

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(1L);
        chatRoom.setName("ChatRoom");

        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent("Hello!");
        message.setDatetime(LocalDateTime.now());

        Message message1 = new Message();
        message1.setSender(sender);
        message1.setRecipient(recipient);
        message1.setContent("Noob!");
        message1.setDatetime(LocalDateTime.now());

        List<MessageDto> listDto = new ArrayList<>();
        listDto.add(mappingService.MessageToDto(message));
        listDto.add(mappingService.MessageToDto(message1));

        when(messageRepository.findAllByRecipientId(recipient.getId()))
                .thenReturn(List.of(message1, message));
        when(getService.getCurrentUser()).thenReturn(sender);

        List<MessageDto> result = messageService.getMyMessages();

        assertEquals(listDto, result);
    }

    @Test
    void TestDelayedMessage(){
        String chatroom = "ChatRoom";
        Long minute = 1L;
        String content = "content";
        Mono<Void> result = messageService.delayedMessage(chatroom,minute,content);
        StepVerifier.create(result).expectComplete().verify();
    }
}