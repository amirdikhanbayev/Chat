package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean online;

    @ManyToMany
    @JoinTable(name = "user_chat_room",
            joinColumns = {@JoinColumn(name = "user_id")}
            , inverseJoinColumns = {@JoinColumn(name = "chat_room_id")})
    private List<ChatRoom> chatRooms;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_rols",
            joinColumns = {@JoinColumn(name = "user_id")}
            , inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Collection<Role> roles;

}
