package com.example.demo.model;


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
    private Chat_room chat_room;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Users user;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Users user1;
    private String content;
    private LocalDateTime datetime;
}
