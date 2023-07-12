package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoomId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Users senderId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Users recipientId;
    private String content;
    private LocalDateTime datetime;
}
