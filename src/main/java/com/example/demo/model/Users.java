package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Boolean online;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_chat_room",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "chat_room_id")})
    private List<Chat_room> chatRooms;
}
