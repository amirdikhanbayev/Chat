package com.example.demo.repository;

import com.example.demo.model.Chat_room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ChatRoomRepository extends JpaRepository<Chat_room, Long> {
    Chat_room findByName(String name);
}
