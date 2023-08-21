package com.example.demo.service.chatroom;

import com.example.demo.model.ChatRoom;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.service.get.GetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
class ChatRoomServiceImplTest {
    @InjectMocks
    private ChatRoomServiceImpl chatRoomService;
    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private GetService getService;


    @Test
    void changeName() {
        String name = "NewRoom";
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(2L);
        chatRoom.setName("ChatRoom");

        Mockito.when(chatRoomRepository.findByName(chatRoom.getName()))
                .thenReturn(chatRoom);

        Mockito.when(chatRoomRepository.save(any())).thenReturn(chatRoom);

        ChatRoom fr = chatRoomService.changeName(chatRoom.getName(), name);

        Mockito.verify(chatRoomRepository).save(chatRoom);
        Assertions.assertEquals(name, fr.getName());
    }

    @Test
    void create(){

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(1L);
        chatRoom.setName("ChatRoom");

        Mockito.when(chatRoomRepository.save(any())).thenReturn(chatRoom);

        ChatRoom chatRoom1 = chatRoomService.create(chatRoom.getName());

        assertEquals(chatRoom.getName(), chatRoom1.getName());

    }
}