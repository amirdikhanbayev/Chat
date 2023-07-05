package com.example.demo.service;

import com.example.demo.model.Users;

import java.util.Optional;

public interface UserService {
    Users create(Users user);

    String changeUsername(Long id, String username);

    String deleteUser(Long id);

    Optional<Users> findByUsername(String username);


}
