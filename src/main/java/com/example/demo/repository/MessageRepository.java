package com.example.demo.repository;

import com.example.demo.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
//    @Modifying
//    @Query("select from message m where m.User1.id = :id")
//    List<Message> getMessagesByIdIs(@Param("id") Long id);
//    @Modifying
//    @Query(value = "select from message m where m.recipient_id = :id", nativeQuery = true)
//    List<Message> getMessagesByIdIs(@Param("id") Long id);

//        @Query(value = "select m.id from Message m where m.recipient.id = :id")
//        List<MesssageDto> findAllByRecipientId11(@Param("id") Long id);

        List<Message> findAllByRecipientId(Long id);
}
