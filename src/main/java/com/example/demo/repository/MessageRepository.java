package com.example.demo.repository;

import com.example.demo.model.Message;
import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {
//    @Modifying
//    @Query("select from message m where m.User1.id = :id")
//    List<Message> getMessagesByIdIs(@Param("id") Long id);
//    @Modifying
//    @Query(value = "select from message m where m.recipient_id = :id", nativeQuery = true)
//    List<Message> getMessagesByIdIs(@Param("id") Long id);

        List<Message> findMessageByRecipientIdId(Long userId);
}
