package com.example.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    @ManyToMany(mappedBy = "chatRooms")
//    private List<Users> users;
    }
//    @ManyToMany
//    @JoinTable(name = "user_chat_room",
//            joinColumns = @JoinColumn(name = "chat_room_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private List<Users> users;}