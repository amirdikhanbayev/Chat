package com.example.demo.repository;

import com.example.demo.model.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
class UsersRepositoryTest {
    @Autowired
    UsersRepository usersRepository;

    @AfterEach
    void tearDown() {
        usersRepository.deleteAll();
    }

    @Test
    void online() {
        Users user = new Users();
        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("password1");
        usersRepository.save(user);

        usersRepository.online(user.getId());

        Users updatedUser = usersRepository.findById(user.getId()).orElse(null);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.isOnline()).isTrue();
    }

    @Test
    void offline() {
        Users user = new Users();
        user.setId(1L);
        user.setUsername("user2");
        user.setPassword("password1");
        user.setOnline(true);
        usersRepository.save(user);

        usersRepository.offline(user.getId());

        Users updatedUser = usersRepository.findById(user.getId()).orElse(null);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.isOnline()).isFalse();
    }
}