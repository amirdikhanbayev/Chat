package com.example.demo.repository;

import com.example.demo.model.ChatRoom;
import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    List<Users> findByOnline(boolean online);
    @Modifying
    @Query("UPDATE Users s set s.online = TRUE where s.id = :id")
    void online(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Users s set s.online = FALSE where s.id = :id")
    void offline(@Param("id") Long id);

//    @Modifying
//    @Query(value = "SELECT user_id from user_chat_room u where u.chat_room_id = :id", nativeQuery = true)
//    List<Long> user_id(@Param("id")Long id);

    List<Users> findUsersByChatRooms(ChatRoom chatRoom);
}
