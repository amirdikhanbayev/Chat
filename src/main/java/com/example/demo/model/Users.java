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

    @ManyToMany
    @JoinTable(name = "user_chat_room",
            joinColumns = {@JoinColumn(name = "user_id")}
            , inverseJoinColumns = {@JoinColumn(name = "chat_room_id")})
    private List<ChatRoom> chatRooms;

    @ManyToMany
    @JoinTable(name = "users_rols",
            joinColumns = {@JoinColumn(name = "user_id")}
            , inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;

}
